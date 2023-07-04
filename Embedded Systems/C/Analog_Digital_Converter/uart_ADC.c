/*
 * lab5.c
 *
 * Created: 4/11/2023 12:25:15 PM
 * Author : eplsn
 */ 
#define F_CPU 16000000L
#define UART_BAUD 9600

#include <ctype.h>
#include <time.h>
#include <string.h>
#include <stdint.h>
#include <stdio.h>
#include <i2cmaster.h>
#include <avr/io.h>
#include <util/delay.h>
#include <util/twi.h>
#include "twimaster.c"

int uart_putchar(char, FILE*);
int uart_getchar(FILE*);

static FILE uart_io = FDEV_SETUP_STREAM(uart_putchar, uart_getchar, _FDEV_SETUP_RW);

static float read_adc(uint8_t pin) {
	ADMUX = (ADMUX & 0xf0) | pin;
	ADCSRA |= 1 << ADSC;    //flag to start conversion bit
	
	while (!(ADCSRA & (1 << ADIF)));
	ADCSRA |= 1 << ADIF;
	
	return 5.0f * (ADCL + (ADCH << 8)) / 1023.0f;
}

#define Dev24C02  0xA2
/* I2C clock in Hz */
#define SCL_CLOCK  100000L

#define DAC_ADDRESS 0b01011000

#if (__GNUC__ * 100 + __GNUC_MINOR__) < 304
#error "This library requires AVR-GCC 3.4 or later, update to newer AVR-GCC compiler !"
#endif

/** defines the data direction (reading from I2C device) in i2c_start(),i2c_rep_start() */
#define I2C_READ    1

/** defines the data direction (writing to I2C device) in i2c_start(),i2c_rep_start() */
#define I2C_WRITE   0

int main(void)
{
	stdout = stdin = &uart_io;
	ADMUX |= 0b01 << REFS0;
	ADCSRA = 1 << ADEN | 0b110 << ADPS0;
	
	//
	UCSR0A = 1 << U2X0;
	UBRR0L = (F_CPU / (8UL * UART_BAUD)) - 1;
	
	UCSR0B = 1 << TXEN0 | 1 << RXEN0;
    /* Replace with your application code */
    while (1) {
		char input[50];
		scanf("%49s",input);
		
		if (strcmp(input, "G") == 0) {
			float voltage = read_adc(0);
			printf(">>> G\n");
			printf("v = %.3f V\n", voltage);
			
		}
		
		else if (*input == 'S') {
			int outputChannel = -1;
			float outputVoltage = -1;

			// use sscanf to scan the string for the two float values
			sscanf(input, "S,%d,%f",&outputChannel, &outputVoltage);
			
			if ((outputChannel == 0 || outputChannel == 1) && (outputVoltage <= 5 && outputVoltage >= 0)) {
				uint8_t nv = 51 * outputVoltage;
				i2c_init();
				i2c_start_wait(DAC_ADDRESS | I2C_WRITE);
				i2c_write(outputChannel);
				i2c_write(nv);
				i2c_stop();
				printf(">>> S,%d,%.3f\n", outputChannel, outputVoltage);
				printf("DAC channel %d set to %.3f V (%dd)\n", outputChannel, outputVoltage, (uint8_t) outputVoltage*51);
			}
			else {
				printf("choose channel 0 or 1, output voltage must be within 0-5\n");
			}
		}
		else if (*input == 'W') {
			int sineChannel = -1;
			int freq = -1;
			int numWaves = -1;
			
			sscanf(input, "W,%d,%d,%d",&sineChannel, &freq, &numWaves);
			int sineTable[] = {128, 141, 153, 165, 177,	188, 199, 209, 219, 227, 234, 241, 246, 250, 254, 255, 255, 255, 254, 250, 246, 241, 234, 227, 219 ,209 ,199, 188, 177, 165 ,153, 141 ,128, 115, 103, 91, 79, 68 ,57 ,47, 37, 29, 22, 15, 10, 6 ,2, 1, 0, 1 ,2, 6 ,10 ,15 ,22 ,29 ,37 ,47, 57 ,68 ,79 ,91 ,103 ,115};

			if ((sineChannel == 0 || sineChannel == 1) && (10 == freq || freq == 20) && (1 <= numWaves && numWaves <= 100)) {
				i2c_init();
				printf(">>> W,%d,%d,%d\n", sineChannel, freq, numWaves);

				printf("Generating %d sine wave cycles with f=%d Hz on DAC channel %d\n", numWaves, freq, sineChannel);

				for (int k = 0; k < numWaves; k++) {
					for (int j = 0; j < 64; j++) {
						i2c_start_wait(DAC_ADDRESS | I2C_WRITE);
						i2c_write(sineChannel);
						i2c_write(sineTable[j]);
						i2c_stop();
						if (freq == 10) {
							_delay_ms(1.262);
						}
						else {
							_delay_ms(0.482026);
						}
					}
				}
			}
			else {
				printf("choose channel 0 or 1, frequency within 10-20, and number of waves within 1-100\n");
			}
		}
		else {
			printf("invalid input\n");
		}
    }
}


int uart_putchar(char c, FILE *f) {
	if (c == '\n') {
		uart_putchar('\r', f);
	}

	while ( !( UCSR0A & (1<<UDRE0)) );
	UDR0 = c;

	return 0;
}

int uart_getchar(FILE* f) {
	while ( !(UCSR0A & (1<<RXC0)) );
	/* Get and return received data from buffer */
	uint8_t c = UDR0;
	return c;
}
