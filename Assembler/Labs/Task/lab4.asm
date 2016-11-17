model small
.stack 300h
.386
.data

	output db 50
	db 50 dup('$')
	number dq 348.285314
	pow dw 1
	intCount dw 1
	fsttmp dw ?
	dwTen dw 10
	dqZero dq 0.0
	zero db '0'
	lastDel dw 0
	dqVar dq ?
	dwVar dw ?
.code
start:
	mov ax, @data
	mov ds, ax
	finit
	;магия
  	fnstcw fsttmp
  	or     [fsttmp], 0C00h
  	fldcw  fsttmp

  	;РИСУЮ ЦЕЛУЮ ЧАСТЬ
  	fld number
  	@repeat:
  	  	ficom dwTen
    	fstsw ax
    	fwait
    	sahf
    	jb @next
  		fidiv dwTen
  		mov ax, pow
  		mul dwTen
  		mov pow, ax
  		inc intCount
  		jmp @repeat
  	@next:

  	mov cx, intCount
  	mov si, 0
  	fstp

  	@loop1:
  		fld number
  		fidiv pow
  		fisub lastDel
  		frndint
  		fistp dqVar
  		mov al, byte ptr dqVar
  		add al, zero
  		mov output[si],al
  		inc si

		mov al, byte ptr dqVar
  		xor ah, ah
  		add lastDel, ax
  		mov ax, lastDel
  		mul dwTen
  		mov lastDel, ax

  		mov ax, pow
  		div dwTen
  		mov pow,ax

  	loop @loop1

  	mov output[si], '.'
  	inc si
  	;РИСУЮ ДРОБНУЮ ЧАСТЬ
  	fld number
  	frndint
  	fistp dwVar
  	fld number
  	fisub dwVar
  	; 0.12341
  	@repeat2:
  		fcom dqZero
    	fstsw ax
    	fwait
    	sahf
    	jbe @print
    	fimul dwTen
    	fst dqVar ;clone
    	fld dqVar
    	frndint
    	fistp dwVar
    	fisub dwVar
  		mov al, byte ptr dwVar
  		add al, zero
  		mov output[si],al
  		inc si
      cmp si, 15
      je @print
  		jmp @repeat2
    @print:
	mov ah, 09h
    mov dx, offset output
    int 21h

@exit:
	mov ah,0
    int 16h
    mov ax,4C00h
    int 21h
end start