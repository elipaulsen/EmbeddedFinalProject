sbi DDRB,0;ser
sbi DDRB,1;srclk
sbi DDRB,2;rclk
cbi DDRB,7;channela
cbi DDRB,6;channelb


.equ zero=0x3f
.equ one=0x06
.equ two=0x5b
.equ three=0x4f
.equ four=0x66
.equ five=0x6d
.equ six=0x7d
.equ seven=0x07
.equ eight=0x7f
.equ nine=0x6f
.equ ten = 0b01110111
.equ eleven =0b01111100
.equ twelve = 0b00111001
.equ thirteen = 0b01011110
.equ fourteen = 0b01111001
.equ fifteen = 0b01110001
.equ dash=0b01000000
 
ldi R18, dash ;display register
ldi R19, 0 ;keeps track of number
ldi R24,0
ldi R28,0
ldi R27,0b00000000
rcall display

start:
      rcall pollRPG
      ldi R24,0
      rcall pollButton
      jmp start
sampleDelay:
      .equ countDelay = 0x04E2
      nop
      ldi r30, low(countDelay)            
      ldi r31, high(countDelay)
d1:
      ldi   r29, 0xD4         
d2:
                                                                        
      dec   r29                     
      brne  d2                                        
      sbiw r31:r30, 1                           
      brne d1
      inc R24                                               
      
      ret   
pollButton:
`     sbic PIND,6
      rcall buttonPress
      ret
buttonPress:
      rcall numberSamples
      cpi r24, 40
      brsh reset;


      inc R28
      rcall check
      ret
check:
      cpi R28,1
      breq digit1
      cpi R28,2
      breq digit2
      cpi R28,3
      breq digit3
      cpi R28,4
      breq digit4
      cpi R28,5
      breq digit5
      ret
digit1:
      cpi R19,5
      breq right
      ret
digit2:
      cpi R19,4
      breq right
      ret
digit3:
      cpi R19,3
      breq right
      ret
digit4:
      cpi R19,9
      breq right
      ret
digit5:
      cpi R19,3
      breq right
      rcall finalCheck
      ret
finalCheck:
      ldi R29, 5
      cp R27,R29
      breq unlocked
      rjmp locked
      ret
locked:
      ;something
      ldi R18, 0b00001000
      rcall display
      rjmp locked
unlocked:
      ldi R18, ten
      rcall display
      rjmp unlocked


      ;sbi PORTB,5
      ;ldi R18, 0b10000000
      ;rcall display
      ;have delay

      ;ldi R18, ten
      ;ldi R19, 0
      ;ldi R24,0
      ;ldi R28,0
      ;ldi R27,0b00000000
      ;rcall display
      ;rjmp start
right:
      inc R27
      ret
numberSamples:
      rcall sampleDelay
      sbis PIND, 6
      ret
      rjmp numberSamples
reset:
      ldi R24,0
      ldi R18, dash
      ldi R19, 0
      ldi R28,0
      ldi R27,0b00000000
      rcall display
      rjmp start
pollRPG:
      rcall readRPG
      mov R22,R21
      andi R22,0b00000011
      cp R16, R22
      brne changed
      ret
changed:
      lsl R21
      lsl R21
      or R21,R16
      rcall checkSequence
checkSequence:
      ldi R23, 0b01001011
      ldi R24, 0b10000111
      cp R21,R23
      breq counterClockwise
      cp R21,R24
      breq clockwise
      rjmp start
clockwise:
      rcall changeValuePlus
counterClockWise:
      rcall changeValueMinus
readRPG:
      in R16, PIND
      andi R16,0b00110000
      lsr R16
      lsr R16
      lsr R16
      lsr R16
      ret


changeValuePlus:
      inc R19
      cpi R19,1
      breq show1
      cpi R19,2
      breq show2
      cpi R19,3
      breq show3
      cpi R19,4
      breq show4
      cpi R19,5
      breq show5
      cpi R19,6
      breq show6
      cpi R19,7
      breq show7
      cpi R19,8
      breq show8
      cpi R19,9
      breq show9
      cpi R19,10
      breq show10
      cpi R19,11
      breq show11
      cpi R19,12
      breq show12
      cpi R19,13
      breq show13
      cpi R19,14
      breq show14
      cpi R19,15
      breq show15
      cpi R19,16
      breq rollover
show1:
      ldi R18,one
      rcall display
      rjmp start
show2:
      ldi R18,two
      rcall display
      rjmp start
show3:
      ldi R18,three
      rcall display
      rjmp start
show4:
      ldi R18,four
      rcall display
      rjmp start
show5:
      ldi R18,five
      rcall display
      rjmp start
show6:
      ldi R18,six
      rcall display
      rjmp start
show7:
      ldi R18,seven
      rcall display
      rjmp start
show8:
      ldi R18,eight
      rcall display
      rjmp start
show9:
      ldi R18,nine
      rcall display
      rjmp start
show10:
      ldi R18,ten
      rcall display
      rjmp start
show11:
      ldi R18,eleven
      rcall display
      rjmp start
show12:
      ldi R18,twelve
      rcall display
      rjmp start
show13:
      ldi R18,thirteen
      rcall display
      rjmp start
show14:
      ldi R18,fourteen
      rcall display
      rjmp start
show15:
      ldi R18,fifteen
      rcall display
      rjmp start

rollover:
      ldi R19,0
      ldi R18,zero
      rcall display
      rjmp start
changeValueMinus:
      cpi R19,0
      breq rolloverMinus
      dec R19
      cpi R19,1
      breq show1
      cpi R19,2
      breq show2
      cpi R19,3
      breq show3
      cpi R19,4
      breq show4
      cpi R19,5
      breq show5
      cpi R19,6
      breq show6
      cpi R19,7
      breq show7
      cpi R19,8
      breq show8
      cpi R19,9
      breq show9
      cpi R19,0
      breq show0
      cpi R19,10
      breq show10
      cpi R19,11
      breq show11
      cpi R19,12
      breq show12
      cpi R19,13
      breq show13
      cpi R19,14
      breq show14
      cpi R19,15
      breq show15
      rjmp start
show0:
      ldi R18,zero
      rcall display
      rjmp start
rolloverMinus:
      ldi R19,15
      ldi R18, fifteen
      rcall display
      rjmp start
display:
      push R18
      push R17
      in R17, SREG
      push R17
      ldi R17, 8
loop:
      rol R18
      BRCS set_ser
      cbi PORTB,0
      rjmp end
set_ser:
      sbi PORTB,0
end:
      sbi PORTB,1
      cbi PORTB,1
      dec R17
      brne loop
      sbi PORTB,2
      cbi PORTB,2
      
      pop R17
      out SREG, R17
      pop R17
      pop R18
      
      ret
.exit
