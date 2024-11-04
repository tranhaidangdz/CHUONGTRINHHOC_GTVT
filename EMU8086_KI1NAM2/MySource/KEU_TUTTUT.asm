;cho keu lien tuc,bam phim bat ky de ket thuc
.model small
.stack 100h
.data
    tb1 db 13,10,'keu tut tut:$'
    tb2 db 13,10,'bam q hoac Q de ket thuc$'
    
.code 
main proc
    mov ax,@data
    mov ds,ax
    
    mov ah,9
    lea dx,tb1
    int 21h
    
    back:
    mov ah,2
    mov dl,7
    int 21h
    
    mov ah,1
    int 16h
    jz back
    
    mov ah,0
    int 16h
    cmp al,'q'
    je thoat 
    cmp al,'Q'
    je thoat
    jmp back
    
thoat:    
    mov ah,4ch
    int 21h
    main endp
end main
