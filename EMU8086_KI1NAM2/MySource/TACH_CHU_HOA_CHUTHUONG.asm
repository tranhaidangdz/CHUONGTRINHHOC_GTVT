.model small
.stack 100h
.data
    str db 50 dup('$')
    hoa db 50 dup('$')
    thuong db 50 dup('$')
    tb1 db 13, 10, 'nhap chuoi: $'   
    tb2 db 13, 10, 'chuoi in hoa: $' 
    tb3 db 13, 10, 'chuoi thuong: $' 
    tb4 db 13, 10, 'chuoi vua nhap: $'
.code
    main proc
        mov ax, @data
        mov ds, ax
        
        mov ah, 9
        lea dx, tb1  
        int 21h     
        
        mov bx, 0
        mov si, 0
        mov di, 0
        
nhaptiep:                      
        mov ah, 1
        int 21h
        cmp al, 13
        je enter 
         
        mov str[bx], al
        inc bx 
        
        cmp al, 97
        jge chuthuong 
        
        mov hoa[di], al
        inc di
        jmp nhaptiep
chuthuong:
        mov thuong[si], al   
        inc si
        jmp nhaptiep 
    
                     
enter:  
        
        mov ah, 9
        lea dx, tb4 ;In ra chuoi vua nhap
        int 21h
        
        mov ah, 9
        lea dx, str
        int 21h
        
        mov ah, 9
        lea dx, tb2  ;in ra chuoi hoa
        int 21h
        
        mov ah, 9
        lea dx, hoa
        int 21h
               
        mov ah, 9
        lea dx, tb3 ;in ra chuoi thuong 
        int 21h
        
        mov ah, 9
        lea dx, thuong
        int 21h       
               
        mov ah, 4ch
        int 21h
    main endp
end main
