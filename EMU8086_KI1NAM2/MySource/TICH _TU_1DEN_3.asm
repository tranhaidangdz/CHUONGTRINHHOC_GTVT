.MODEL SMALL
.stack 100h
.data
crlf db 13,10,'$'
tb1 db 13,10,'nhap vao n = $'
tb2 db 13,10,'tich cac so tu 1-> n =$'
tich db ?
.code

main proc
    mov ax,@data
    mov ds,ax
    
    mov ah,9
    lea dx,tb1
    int 21h
nhaplai:    
    mov ah,1
    int 21h
    
    cmp al,48
    jl nhaplai
    cmp al,57
    jg nhaplai
    
    sub al,48
    mov cl,al  ; cl= so n nhap vao 
    
    mov bh,1  ;tao bien i=1
    mov al,1  ;tao bien tich al=1

nhantiep:
    mul bh  ;al=al*bh
    cmp bh,cl
    jge thoat
    inc bh  ;tang bien dem bh++ 
    jmp nhantiep
    
thoat:  
    add al,48
    mov tich,al 
    
    mov ah,9
    lea dx,tb2
    int 21h
    
    mov ah,2
    mov dl,tich
    int 21h  
    
    mov ah,4ch
    int 21h
    
    main endp
    end main
    
    
    
    
    
    