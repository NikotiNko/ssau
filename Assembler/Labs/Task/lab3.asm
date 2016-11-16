model small
.stack 300h
.386
.data
	fio db "Nikitin Artem",0
	startY dw 50
	startX dw 20
	dbVar db 0
	two db 2
	currentX dw 0

.code
	draw PROC   ; рисуем 1 строку бит
		pusha
		mov cx, 8
		mov dx, startY
		mov al, 80h ; кладем 1000 0000 bin
		xor ah,ah
		mov currentX, 0
		@for:
			mov bl, al
			and bl, dbVar ; в dbVar лежит текущий байт
			cmp bl, 0
			je @continue  ; рисуем
			push ax
			push cx
			mov ah, 0Ch
			mov al, 8
			mov cx, startX
			add cx, currentX
			int 10h
			pop cx
			pop ax
		@continue:
			div two
			inc currentX
		loop @for
		popa
		ret
	draw ENDP

start:
	mov ax, @data
	mov ds, ax
	;переходим в режим рисовалки
	mov ax, 0011h
	int 10h                 ;говнокод
    push 0A000h
    pop es
    push 0F000h
    pop gs
    mov bx,offset fio
next:  ; получение нового символа
	movzx si,byte ptr [bx]
    lea esi,[esi*8+0FA6Eh] ;здесь лежит адрес символа в gs
    mov cx,8

@lopa:  ; каждый цикл - строка бит
	mov ah,gs:[si] ; в dbVar лежит текущий байт шрифта
	mov dbVar, ah
	CALL draw
	inc si
	inc startY
loop @lopa
    sub startY, 8
    inc bx
    add startX, 8
    cmp byte ptr [bx],0
    jnz next

@exit:
	mov ah,0
    int 16h
    mov ax,4C00h
    int 21h
end start