    .MODEL SMALL
    .STACK 100H
    .DATA
    tb1 db 13,10,'nhap vao chuoi:$'
    tb2 db 13,10,'chuoi ban vau nhap la:$'
    str db 50 dup('?') 
    .code 
    
    main proc
        mov ax,@data
        mov ds,ax
       
        
        mov ah,9
        lea dx,tb1
        int 21h
        
        mov bx,0
nhaptiep:
        mov ah,1
        int 21h
        cmp al,13
        je enter
        
        mov str[bx],al
        inc bx
        jmp nhaptiep
enter:    
        mov str[bx],'$'  
        
        mov ah,9
        lea dx,tb2
        int 21h
        
        mov cx,bx
        mov bx,0

intiep:  
        
        mov ah,2
        mov dl,str[bx]
        int 21h
        inc bx
        cmp bx,cx
        jle intiep
        
        mov ah,4ch
        int 21h
main endp
    end main
       