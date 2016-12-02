.model tiny
.386
.stack 100h

.data
	Int_21h_old dd ?
	buffer dw 20
	db 20 dup(?)
.code

Int_21h_new proc
	cmp ah, 1
	ja @do

	jmp Int_21h_old
		
	@do:
		pushf
		call Int_21h_old

		cmp al,'A'
		jb @return
		cmp al,'Z'
		ja @next
		add al, byte ptr 33
		jmp @return

	@next:
		cmp al,'a'
		jb @return
		cmp al,'z'
		ja @return
		sub al, byte ptr 33
	@return:

		iret
Int_21h_new endp

START:
	mov ax, @data
	mov ds, ax
	
	mov ax, 0
	mov es, ax
	
				cli
					;ah содержит номер функции
					;mov ax,3521h
					;al указывает номер прерывания, адрес (или вектор) которого нужно получить
					;int 21h
					;Теперь в es:bx адрес (вектор) прерывания 21h (es — сегмент, bx — смещение)

					;Обратите внимание на форму записи
					;mov word ptr Int_21h_old,bx
					;mov word ptr Int_21h_old+2,es

					;Итак, адрес сохранили. Теперь перехватываем прерывание:

					;mov ax, cs
					;mov ds, ax
					;mov ax, 2521h
					;mov dx,offset Int_21h_new ;ds:dx должны указывать на наш обработчик
					 ;(т. е. Int_21h_proc)

					;int 21h
				
					;mov dx,offset START
					;int 27h


					mov ax, es:[16h * 4]
					mov word ptr Int_21h_old, ax
					mov ax, es:[16h * 4 + 2]
					mov word ptr Int_21h_old + 2, ax
					
					mov ax, offset Int_21h_new
					mov dx, cs
					mov es:[16h * 4], ax
					mov es:[16h * 4 + 2], dx

				sti
				
					mov dx, offset buffer
					mov ah, 0Ah
					int 21h
				
				mov ax,es
				mov ds,ax
				mov dx,offset START
				int 27h

				;cli

					;mov dx, word ptr Int_21h_old
					;mov ds,word ptr Int_21h_old+2

					;mov ax,2521h
					;int 21h
				;	mov ax, word ptr Int_21h_old
				;	mov es:[16h * 4], ax
				;	mov ax, word ptr Int_21h_old + 2
				;	mov es:[16h * 4 + 2], ax
				;sti
				
	mov ax, 0700h
	int 21h
	
	mov ah, 4Ch
	int 21h
	
	ret

END START