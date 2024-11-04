 ;NHAP 1 MANG VA TINH TONG CAC SO CHAN
 .model small
 .stack 100h
 .data
 tb1 db 13,10,'nhap vao 1 so:$' 
 tb2 db 13,10,'tong so chan=$'
 str db 50 dup('$')  
 tong db 0
 .code 
 main proc 
    mov ax,@data
    mov ds,ax
    
    mov si,0
    mov bl,2
    
    mov ah,9
    lea dx,tb1
    int 21h
nhaptiep:    
    mov ah,1
    int 21h  
    mov ah,0
     cmp al,13
     je enter
    sub al,48
    
    div bl
    cmp ah,0
    je sochan 
    jmp nhaptiep
sochan: 
    mov ch,0
    mov cl,al
    add tong,cl
    jmp nhaptiep
    
enter: 
    mov ah,9
    lea dx,tb2
    int 21h 
    mov ah,0
    
    mov bx,0
    mov bx,10
    mov cx,0
    mov al,tong
chiatiep:   
    mov dx,0
    div bx
    add dx,48
    push dx 
    inc cx
    cmp ax,0
    jg chiatiep
intiep:
    mov ah,2
    pop dx
    int 21h
    loop intiep
    
    mov ah,4ch
    int 21h
    
    main endp 
 end main