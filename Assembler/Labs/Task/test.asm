model small
.stack 300h
.386
.data

	output db 18
	db 18 dup('$')
	number dq 1.1
	number2 dq 1.2
	pow dw 1
	intCount dw 0
	fsttmp dw ?
	dwTen dw 10
	dqTen dq 10
	dqTwo dq 2
	dqZ dq 0
	varQ dq ?
.code
start:
	mov ax, @data
	mov ds, ax
	finit
	;магия
  	;fnstcw fsttmp
  	;or     [fsttmp], 0C00h
  	;fldcw  fsttmp

  	fld number
  	fld number2
  	fst dqZ
  	@repeat:
  	  	fidiv dwTen
  	  	fst dqZ
  	  	fcom dqTen
    	fstsw ax
    	fwait
    	sahf
    	jb @next
  		;mov ax, pow
  		;mul dwTen
  		;mov pow,ax
  		inc intCount
  		jmp @repeat
  	@next:
  	mov ax, pow
  	mov dx, intCount
	mov si, 0
	mov output[si],'5'
	mov ah, 09h
    mov dx, offset output
    int 21h

@exit:
	mov ah,0
    int 16h
    mov ax,4C00h
    int 21h
end start