MODEL SMALL
.386
.STACK 100h
.DATA
 old_int9h dd ?
 symbols db 4 dup (0)

 enter1_msg db 10,13,'enter first symbol: $'
 enter2_msg db 10,13,'enter second symbol: $'
 error_msg db 10,13,'enter error$'
 installed_msg db 10,13,'Installed$'
 uninstalled_msg db 'Uninstalled$'

.CODE
start:
 jmp initialize                 

new_int9h proc    
 pusha
 push es
 push ds
 pushf
    
 xor bx,bx
 xor dx,dx

 call cs:old_int9h

 push 0040h
 pop ds                          
    
 mov di,ds:001Ah        
 cmp di,ds:001Ch              
 je quit                       

 mov ax,[di]                         

 cmp al,cs:symbols[0]              
 jne check_big_first               
    
 mov ds:001Ch,di                 
 xor ax,ax
 mov ah,5h
 xor cx,cx
 mov cl,cs:symbols[2]
 int 16h                          
 jmp quit

check_big_first:
 cmp al,cs:symbols[1]              
 jne check_second              
    
 mov ds:001Ch,di                    
 xor ax,ax
 mov ah,5h
 xor cx,cx
 mov cl,cs:symbols[3] 
 int 16h                         
 jmp quit

check_second: 
 cmp al,cs:symbols[2]           
 jne check_big_second            
    
 mov ds:001Ch,di                    
 xor ax,ax
 mov ah,5h
 xor cx,cx
 mov cl,cs:symbols[0]
 int 16h                            
 jmp quit

check_big_second:
 cmp al,cs:symbols[3]               
 jne quit                           
    
 mov ds:001Ch,di                   
 xor ax,ax
 mov ah,5h
 xor cx,cx
 mov cl,cs:symbols[1]   
 int 16h                           

quit:   
 pop ds
 pop es
 popa
 iret                               
new_int9h endp 

initialize:                          
 mov ax,@data 
 mov ds,ax
 
 mov dx,offset enter1_msg        
 mov ah,9h
 int 21h 
 xor di,di
 call get_symbol 					 
 
 mov dx,offset enter2_msg        
 mov ah,9h
 int 21h 
 inc di
 call get_symbol 					
 
 push ds
 push 0        						
 pop ds
    
 mov eax,ds:09h*4				  	  
 mov cs:old_int9h,eax 			     

 mov ax,cs
 shl eax,16 						  
 
 mov ax,offset new_int9h 			 
 mov ds:09h*4,eax 				 

 pop ds
 mov dx,offset installed_msg        
 mov ah,9h
 int 21h 
 mov dx,offset initialize           
 int 27h                              

get_symbol proc
 mov ah,01h
 int 21h

 cmp al,'A'
 jl error
 cmp al,'Z'
 ja check_small
 push ax
 add ax,20h
 mov symbols[di],al
 inc di
 pop ax
 mov symbols[di],al
 jmp all_ok

check_small:
 cmp al,'a'
 jl error
 cmp al,'z'
 ja error
 mov symbols[di],al
 inc di
 sub ax,20h
 mov symbols[di],al
all_ok:
 ret

error:
 mov dx,offset error_msg        
 mov ah,9h
 int 21h 
 mov ah, 4Ch
 int 21h
get_symbol endp

end start