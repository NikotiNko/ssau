model small
.stack 300h
.386
.data
    leftBoundMsg db 10,13,"Input left bound: $"
    rightBoundMsg db 10,13,"Input right bound: $"
    errMsg db 10,13,"Incorrect input, an error ocurred$"
    leftX dq 0
    rightX dq 0
    buffer db 17
    db 17 dup(0)
    arrayX dq 640 dup(0)
    arrayY dq 640 dup(0)
    screenArrayY dw 640 dup(0)
    maxY dq 0
	minY dq 0
    errFlag db 0
	dotPointer dw 0
	ten dw 10
	three dw 3
	dwVar dw 0
	dqVar dq 0
	screenWidth dd 639
	screenHeigth dd 479
	videoMode db ?

    

.code
    inputNumber PROC
        pusha
        mov errFlag,0
        mov ah, 0Ah
        mov dx, offset buffer
        int 21h
        fldz 
        mov si, 2
        cmp buffer[si],0dh
        je @error
        cmp buffer[si],'.'
        je @error
        cmp buffer[si],'+'
        je @mark1
        cmp buffer[si],'-'
        jne @mark2 
        @mark1: inc si
        	cmp buffer[si],0dh
        	je @error
        	cmp buffer[si],'.'
        	je @error
        @mark2: cmp buffer[si],'.'
        	je @float
        	cmp buffer[si],0dh
        	je @onEnter
        	cmp buffer[si],'0'
        	jb @error
        	cmp buffer[si],'9'
        	ja @error
        	mov al,buffer[si]
        	sub al,'0'
        	xor ah,ah
        	fimul ten
        	mov dwVar, ax
        	fild dwVar
        	fadd
        	inc si
        	jmp @mark2

        @float:
            mov dotPointer, si
            fldz
            xor bx,bx
            mov bl,buffer[1]
            mov si,bx
            inc si
                
        @repeat:
            cmp buffer[si],'.'
            je  @endFloat
            cmp buffer[si],'0'
            jb @error
            cmp buffer[si],'9'
            ja @error
            mov al, buffer[si]
            sub al,'0'
            xor ah,ah
            mov dwVar, ax
            fild dwVar
            fadd
            fidiv ten
            dec si
            jmp @repeat
        @endFloat: cmp si, dotPointer
            jne @error
            fadd
        @onEnter:
            cmp buffer[2],'-'
            jne @end
            fchs
            jmp @end
        @error:
            mov errFlag, 1
        @end:
            popa
            ret
    inputNumber ENDP

    processX PROC
        pusha
		fld rightX
		fsub leftX
		fidiv screenWidth
		fstp dqVar
		mov cx,639
		mov si,0
		fld leftX
		@for:
			fst arrayX[si]
			fld dqVar
			fadd
			add si,8
		loop @for
		popa
		ret
	processX ENDP

	calcFunction PROC
	    pusha
		mov cx,639
		mov si,0
		@for1:
			fld arrayX[si]
			fcos
			fmul st,st
			fld arrayX[si]

			fsin
			fst dqVar

            fmul dqVar
			fmul dqVar
            fadd

			fidiv three
			fstp arrayY[si]
			add si,8
		loop @for1
		popa
		ret
	calcFunction ENDP

	processY PROC
		pusha 
	    mov cx,639
		mov si,0
		@for2:
			fld arrayY[si]
			fcom maxY
			fstsw ax
			fwait
			sahf
			ja @bigger

			fcom minY
			fstsw ax
			fwait
			sahf
			jb @smaller
			fstp arrayY[si]
			jmp @step
		@bigger:
			fstp maxY
			jmp @step
		@smaller:
			fstp minY
		@step:
			add si,8
		loop @for2

		fild screenHeigth
		fld maxY
		fsub minY
		fdiv
		fstp dqVar
		mov si,0
		mov cx, 639
		mov di, 0
		@for3:
			fld maxY
			fsub arrayY[si]
			fmul dqVar
			frndint
			fistp screenArrayY[di]
			add di,2
			add si,8
		loop @for3
		popa
		ret
	processY ENDP

	draw PROC
		pusha
		mov ah, 0Fh
		int 10h
		mov videoMode, al
		mov ax, 0011h
		int 10h
		mov si,0
		mov cx,639
		mov dwVar, 0
		@for4:
			push cx
			mov ah, 0Ch
			mov al, 8
			mov cx, dwVar
			mov dx, screenArrayY[si]
			int 10h
			add dwVar,1
			add si,2
			pop cx
		loop @for4

		mov ah,0
		int 16h

		mov ah,0
		mov al, videoMode
		int 10h
		popa
		ret
	draw ENDP


MAIN:
    mov ax, @data
    mov ds, ax
    @input:
        finit
        mov ah, 09h
        mov dx, offset leftBoundMsg
        int 21h
        Call inputNumber
        fst leftX
        cmp errFlag,1
        je @errorMsg
        mov ah, 09h
        mov dx, offset rightBoundMsg
        int 21h
        Call inputNumber
        fst rightX
        cmp errFlag,1
        je @errorMsg
        fcom
        fstsw ax
        fwait
        sahf
        jbe @errorMsg
        jmp @callNext

    @errorMsg:
        mov ah, 09h
        mov dx, offset errMsg
        int 21h
        jmp @input

    @callNext:
        Call processX
        Call calcFunction
        Call processY
        Call draw

    @exit:
    	mov ax,4C00h
    	int 21h
end MAIN