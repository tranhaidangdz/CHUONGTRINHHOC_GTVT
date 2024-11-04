                                                .model small
.stack 100h
.data 
a dw ?
b dw ?

.code
main proc
    mov ax,@data
    mov ds,ax 
               
    mov ax,0
    int 33h 
dichuot:
    mov ax,1
    int 33h
    
    mov ax,3
    int 33h;cx chua cot, dx chua hang
    mov a,dx
    mov b,cx
    
    cmp bx,1
    je bamchuot
    
    cmp bx,2
    je bamchuot
    
    cmp bx,4
    je bamchuot
    jmp dichuot
bamchuot:
    mov ax,a
    mov bx,10
    mov cx,0
chiatiep:  
    mov dx,0
    div bx
    push dx
    inc cx
    cmp ax,0
    jg chiatiep
    
inhang:
    mov ah,2  
    
    pop dx  
    add dl,48
    int 21h
    loop inhang
    
    
    mov ah,2 
    mov dl,','
    int 21h 
    
    mov ax,b
    mov bx,10
    mov cx,0
chiatiep2:  
    mov dx,0
    div bx 
    
    push dx
    inc cx
    cmp ax,0
    jne chiatiep2
    
incot:
    mov ah,2
    pop dx 
    add dx,48 
    int 21h
    loop incot
    mov ah,2
    mov dl,13
    int 21h
     
    jmp dichuot
    
 thoat:   
    mov ah,4ch
    int 21h
    
    main endp 
end main

;lenh ngat chuot :33h   : phai thuc hien voi thanh ax
;-ham 00: khoi tao chuot 
;mov ax,o
;int 33h
;-ham 01 hien chuot
;mov ax,1
;int 33h 
;-ham 02: an di chuot(opacity mouse)
;mov ax,02
;int 33h 
;-ham 03: trang thai an chuot
;mov ax,03
;int 33h
; => bx chua trang thai nut bam (bit 0:nut trai, bit 1:nut phai, bit 2: nut giua)
;cx: chua toa do cot
;dx: chua toa do hang 
