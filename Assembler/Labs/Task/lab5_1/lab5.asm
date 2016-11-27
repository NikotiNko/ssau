.model small
.386
.stack 100h

.data
		int_ip_address dw ?
		int_cs_segment dw ?
		
	len dw 00F0h
	str1 db 0F1h dup(?)
.code
START:
	mov ax, @data
	mov ds, ax
	
	mov ax, 0
	mov es, ax
	
				cli
					mov ax, es:[09h * 4]
					mov int_ip_address, ax
					mov ax, es:[09h * 4 + 2]
					mov int_cs_segment, ax
					
					mov ax, offset Int_19h_new
					mov dx, cs
					mov es:[09h * 4], ax
					mov es:[09h * 4 + 2], dx
				sti
				
					mov dx, offset len
				mov ah, 0Ah
				int 21h
				
				cli
					mov ax, int_ip_address
					mov es:[09h * 4], ax
					mov ax, int_cs_segment
					mov es:[09h * 4 + 2], ax
				sti
				
	mov ax, 0700h
	int 21h
	
	mov ah, 4Ch
	int 21h
	
	ret

	
;include Console.asm

	
Int_19h_new proc

		pushf
		call dword ptr int_ip_address
		
		cli
		
		push bx
		push ds
		push 040h
		pop ds
		
		mov bx, word ptr ds:[001Ch];0:041Ch
		
		sub bx, 2
		cmp bx, 1Eh - 2
		jne @if_2_break
			mov bx, 1Eh + 15 * 2
	@if_2_break:
		
		mov al, byte ptr ds:[bx]
		
		cmp al, 'z'
		jne @if_1_else
			mov byte ptr ds:[bx], 'a'
			jmp @if_1_break
	@if_1_else:
		cmp al, 'a'
		jne @if_1_break
			mov byte ptr ds:[bx], 'z'
	@if_1_break:
	
		cmp al, 'Z'
		jne @if_3_else
			mov byte ptr ds:[bx], 'A'
			jmp @if_3_break
	@if_3_else:
		cmp al, 'A'
		jne @if_3_break
			mov byte ptr ds:[bx], 'Z'
	@if_3_break:
		
		pop ds
		pop bx
		
		sti
		
	iret
Int_19h_new endp
	
END START