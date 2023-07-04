;
; lab4.asm
;
; Created: 3/26/2023 2:09:13 PM
; Author : eplsn
;

;interrupts
.cseg
.org 0x0000
  jmp reset
.org 0x000a
  jmp isr_rpg
.org 0x0006
  jmp isr_pb


.org INT_VECTORS_SIZE
  RESET:
    ldi r17, 0b00000101 ;enable pcint2
    sts PCICR, r17
    
    ldi r17, 0b00110000
    sts PCMSK2, r17

    ldi r17, 0b00000001
    sts PCMSK0, r17
    

init:
  ldi r30, 1
  ldi r19, 1
  sbi DDRB, 3 ;enable line
  sbi DDRB, 5 ;rs ine
  sbi DDRD, 3 ;pwm

  ;lcd data pins
  sbi DDRC, 0
  sbi DDRC, 1
  sbi DDRC, 2
  sbi DDRC, 3

  ;timer init
  ldi r20, 0
  out TCCR0A, r20
  ldi r20, 2
  out TCCR0B, r20

  rcall configurePWM

  cbi PORTB, 3 ; enable low

  rcall configureLCD

  ldi r20, 199

  rcall lcd

  sei
  rjmp main

configureLCD:
  ;Wait for 200 ms
  rcall delay_100ms
  rcall delay_100ms
  rcall delay_100ms
  rcall delay_100ms
  ;Set the device to 4-bit mode
  ldi r28, 0b0010
  out PORTC, r28
  rcall pulseEnable
  ;Wait 5 ms
  rcall delay_5ms

  ldi r28, 0b00101000
  rcall commandLCD

  ;Wait at least 5ms
  rcall delay_5ms

  ldi r28, 0b00001110
  rcall commandLCD

  ldi r28, 0b00000110
  rcall commandLCD

  ldi r28, 0x01
  rcall commandLCD

  rcall delay_100ms
  rcall delay_100ms

  ret

configurePWM:
  ldi r20, 0b00110011
  sts TCCR2A, r20
  ldi r20, 0b00001001
  sts TCCR2B, r20

  ldi r20, 199	;freq
  sts OCR2A, r20
  ldi r29, 149	;dutyCycle
  sts OCR2B, r29

  ret

; Replace with your application code
main:
  rjmp main
  
pulseEnable:
  sbi PORTB, 3
  rcall delay_100u
  cbi PORTB, 3
  ret
  
delay_100u:
  push r26

  in r1, TCCR0B
  clr r26
  out	TCCR0B, r26

  in r26, TIFR0
  sbr r26, 1 << TOV0
  out	TIFR0, r26

  ldi r26, 58
  out TCNT0, r26
  out TCCR0B, r1

delay_100u_wait:
  in r26, TIFR0
  sbrs r26, TOV0
  rjmp delay_100u_wait

  pop r26
  ret

delay_5ms:
  push r24
  push r25

  ldi r25, 0x00 ; 
  ldi r24, 0x32 ;

delay_5ms_loop:
  rcall delay_100u
  sbiw r25:r24, 1
  brne delay_5ms_loop

  pop r25
  pop r24
  ret

delay_100ms:
  push r24
  push r25

  ldi r25, 0x03
  ldi r24, 0xE2 

delay_100ms_loop:
  rcall delay_100u
  sbiw r25:r24, 1
  brne delay_100ms_loop

  pop r25
  pop r24
  ret

commandLCD:
  cbi PORTB, 5
  swap r28
  out PORTC, r28
  rcall pulseEnable
  rcall delay_100u
  swap r28
  out PORTC, r28
  rcall pulseEnable
  ret

characterLCD:
  sbi PORTB, 5
  swap r27
  sbi PORTD, 3
  out PORTC, r27
  rcall pulseEnable
  rcall delay_100u
  swap r27
  out PORTC, r27
  rcall pulseEnable
  ret

clearDisplay:
  ldi r28, 0x01
  rcall commandLCD
  rcall delay_5ms
  ret
  
  
;------------------------------------------------
;Reads RPG rotations
isr_rpg:
  rcall pollRPG
  reti

pollRPG:
  rcall readRPG
  mov R22,R21
  andi R22,0b00000011
  rcall changed
  ret

readRPG:
  in R16, PIND
  andi R16,0b00110000
  lsr R16
  lsr R16
  lsr R16
  lsr R16
  ret

changed:
  lsl R21
  lsl R21
  or R21,R16
  rcall checkSequence
  ret

checkSequence:
  ldi R23, 0b01001011
  ldi R24, 0b10000111
  cp R21, R23
  breq increaseFanSpeed

  cp R21, R24
  breq decreaseFanSpeed

  ret

decreaseFanSpeed:
  cpi r29, 149
  breq rolloverHigh
  inc r29
  inc r29 
  sts OCR2B, r29
  ret

increaseFanSpeed:
  cpi r29, 1
  breq rolloverLow
  dec r29
  dec r29
  sts OCR2B, r29
  ret

rolloverHigh:
  ldi r29, 147
  ret

rolloverLow:
  ldi r29, 3
  ret


;PUSHBUTTON------------

isr_pb:
  in r18, PINB
  andi r18, 0b00000001
  lsl r30
  or r30, r18
  andi r30, 0b00000011
  cpi r30, 0b00000001
  breq rising
  reti

rising:
  cpi r19, 0
  breq fanON
  cpi r19, 1
  breq fanOFF
  
fanON:
  sts OCR2B, r29
  ldi r19, 1
  rcall lcd
  reti

fanOFF:
  sts OCR2B, r20
  ldi r19, 0
  rcall lcd
  reti

;LCD subroutines
lcd:
  ldi ZL, LOW(2*dcStr) ; Load Z register low of "DC ="
  ldi ZH, HIGH(2*dcStr) ; Load Z register high of "DC ="

  rcall displayString

  ldi ZL, LOW(2*dc) ; Load Z register low of "DC ="
  ldi ZH, HIGH(2*dc) ; Load Z register high of "DC ="

  rcall displayString

  ldi ZL, LOW(2*pct) ; Load Z register low of "DC ="
  ldi ZH, HIGH(2*pct) ; Load Z register high of "DC ="

  rcall displayString

  ldi r28, 0b0011000000
  rcall commandLCD

  ldi ZL, LOW(2*fanStr) ; Load Z register low of "DC ="
  ldi ZH, HIGH(2*fanStr) ; Load Z register high of "DC ="

  rcall displayString


  cpi r19, 0
  breq showOn
  cpi r19, 1
  breq showOff

  ret
  
  showON:
    ldi ZL, LOW(2*off) ; Load Z register low of "DC ="
    ldi ZH, HIGH(2*off) ; Load Z register high of "DC ="
    rcall displayString
    ret

  showOFF:
    ldi ZL, LOW(2*on) ; Load Z register low of "DC ="
    ldi ZH, HIGH(2*on) ; Load Z register high of "DC ="
    rcall displayString
    ret


  rcall displayString
  ret

displayString:
  lpm r27, Z+ ; load the nth char then inc to the next n+1 char
  tst r27 ; check the SREG to see if flag raised
  breq endDisplay
  rcall characterLCD
  rjmp displayString ;loop until all chars displayed

  ret

endDisplay:
  ret

dcStr:           .db " DC: ",0
fanStr:           .db "FAN: ",0
on:				.db " ON          ", 0
off:				.db "OFF          ", 0
pct:					.db "%", 0
dc:			.db "see oscilloscope ",0
