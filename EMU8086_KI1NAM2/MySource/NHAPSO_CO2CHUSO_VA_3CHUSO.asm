.MODEL SMALL
.STACK 100H
.DATA 
tb1 db 13,10, 'nhap so n co 2-> 3 chuso:$'
tb2 db 13,10,'so vua nhap la:$'
a db 0 
chia10 dw 10    ;bien nay de tach so va day vao stack

.code 
main proc
       mov ax,@data
       mov ds,ax
       
       mov ah,9
       lea dx,tb1
       int 21h
       
       mov bl,10  ;gan bien de lat chia 10 khi nhap so
nhaptiep:
    mov ah,1
    int 21h
    
    
    cmp al,13
    je nhapxong
    
    sub al,48
    mov bh,al 
    mov al,a
    mul bl
    add al,bh
    mov a,al
    jmp nhaptiep
    
nhapxong:
    mov ah,9
    lea dx,tb2
    int 21h
    
    mov ah,0
    mov cx,0
    mov al,a
chiatiep:
    mov dx,0
    div chia10
    add dx,48
    push dx
    inc cx
    cmp ax,0
    jg chiatiep
intiep:
    pop dx
    mov ah,2
    int 21h
    loop intiep
    
    mov ah,4ch
    int 21h
 
 main endp
end main

       
       