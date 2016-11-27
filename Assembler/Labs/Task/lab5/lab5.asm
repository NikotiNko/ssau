.model small
.386
.stack 100h

.data
		int_ip_address dw ?
		int_cs_segment dw ?
		
	len dw 0010h
	str1 db 11 dup(?)
.code
START:
	mov ax, @data
	mov ds, ax
	
	mov ax, 0
	mov es, ax
	
				cli
					mov ax, es:[16h * 4]
					mov int_ip_address, ax
					mov ax, es:[16h * 4 + 2]
					mov int_cs_segment, ax
					
					mov ax, offset Int_19h_new
					mov dx, cs
					mov es:[16h * 4], ax
					mov es:[16h * 4 + 2], dx
				sti
				
					mov dx, offset len
				mov ah, 0Ah
				int 21h
				
				cli
					mov ax, int_ip_address
					mov es:[16h * 4], ax
					mov ax, int_cs_segment
					mov es:[16h * 4 + 2], ax
				sti
				
	mov ax, 0700h
	int 21h
	
	mov ah, 4Ch
	int 21h
	
	ret

	
;include Console.asm

	
Int_19h_new proc
		cmp ah, 1
		ja @ok
			jmp dword ptr int_ip_address
		@ok:
		
		pushf
		call dword ptr int_ip_address
		
		cmp al, 'z'
		jne @if_1_else
			mov al, 'a'
			jmp @if_1_break
	@if_1_else:
		cmp al, 'a'
		jne @if_1_break
			mov al, 'z'
	@if_1_break:
		
	iret
Int_19h_new endp
	
END START