model small
.stack 300h
.386
.data
	file_name db "name.txt",0
	s_error   db "Error!",13,10,'$'
	buffer    db 81
	db 81 dup("$")
	file_descriptor dw 1
.code
start:
; open
	mov ax, @data
	mov ds, ax
    mov ah,3Dh
    xor al,al
    mov dx, offset file_name
    xor cx,cx
    int 21h
    jc @error_msg
    
;считывание
	mov [file_descriptor],ax
    mov bx,ax
    mov ah,3Fh
    mov dx, offset buffer
    mov cx,80
    int 21h
    jc @error_msg                 
;вывод на экран
	mov ax,3
	int 10h
    mov ax, 0B800h
    mov es,ax
    mov di,0h
    mov si, 0
    mov ah,05
 @repeat:
    mov al, buffer [si]
    mov es:[di],ax
    add di,2
    inc si
    cmp buffer[si],'$'
    jne @repeat
 
@close_file:
    mov ah,3Eh
    mov bx,[file_descriptor]
    int 21h 
    jnc @exit
    jmp @error_msg
 
@error_msg:
    mov ah,9
    mov dx, offset s_error
    int 21h

@exit:
    mov ah,8
    int 21h
    mov ax,4C00h
    int 21h
end start