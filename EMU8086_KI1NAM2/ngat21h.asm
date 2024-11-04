     .model small
     .stack 100H
     .data
      xuongdong db 13,10,'$'
     .code 
    
     main proc
        mov ax,@data
        mov ds,ax
         
        mov ah,1   ;luu  o thanh al
        int 21h 
        mov bl,al
        
        ;lenh xuong dong: toi in ra ky tu xuong dong
        mov ah,9
        lea dx, xuongdong        ;in ra 1 xau: in tu dx
        int 21h
        ;khi muon in ra: in tu thanh dl
        mov ah,2
        mov dl,bl     ;in ra tu dl: danh cho 1 kytu
        int 21h
        
        mov ah,4ch
        int 21h
        main endp
     end main