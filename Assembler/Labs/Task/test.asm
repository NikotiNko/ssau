model small
.stack 300h
.386
.data

	output db 18
	db 18 dup('$')
	number dq 38.0214512
	numBuffer dq ?
	pow dw 1
	fsttmp dw ?
.code
start:
	mov ax, @data
	mov ds, ax
	finit
	;магия
	fnstcw fsttmp
  	or     [fsttmp], 0C00h
  	fldcw  fsttmp

  	mov numBuffer, number
  	fld numBuffer
  	@lop:
  	fcom 10
  	j
    fstsw ax
    fwait
    sahf
  	fdiv 10

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