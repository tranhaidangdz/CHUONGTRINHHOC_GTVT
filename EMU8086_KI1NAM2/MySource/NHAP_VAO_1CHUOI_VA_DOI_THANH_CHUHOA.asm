.model small
 .stack 100h
 .data
 
 tb1 db 13,10,'nhap vao 1 chuoi:$'
 tb2 db 13,10,'chuoi vua nhap la:$' 

 str db 50 dup('$') 
 .code 
 main proc
    mov ax,@data
    mov ds,ax
     
    lea si,str  
    mov cx,0  
    
    mov ah,9
    lea dx,tb1 
    int 21h
      
    lap:
    mov ah,1
    int 21h   
    mov ah,0
    
    cmp al,13   
    je nhapxong  
    
    cmp al,97 
    jge chuthuong 
    cmp al,97
    jl chuhoa
    
chuthuong:
    sub al,32
chuhoa: 
    mov [si],al 
    inc si    
    
    jmp lap
  
nhapxong: 
 
    mov ah,9
    lea dx,tb2 
    int 21h 
    
hienthi:
   mov dx,0
   mov ah,9
   lea dx,str
   int 21h
  
    
    mov ah,4ch
    int 21h
    
    main endp
 end main   