.model small
.stack 100h
.data
    s1 db 'nhap n: $'
    s2 db 10, 13, 'in n: $'
    a db 0
.code
    main proc
        
        mov ax, @data
        mov ds, ax
        
        mov ah, 9
        lea dx,s1
        int 21h 
        
        mov bl,10
        
    nhaptiep:
        mov ah, 1 
        int 21h
        
        mov ah, 0    
        cmp al, 13 
        je enter
        sub al, 48
        mov bh, al
        mov al, a                                 
        mul bl    
        
        add al,bh 
        mov a,al 
        
        jmp nhaptiep  
        
    enter: 
        mov ah,9  
        lea dx, s2
        int 21h   
        
        mov cl,0  
        mov al,a  
    
    chiatiep:
        mov ah,0  
        div bl    
        cmp al,0  
        je inso   
        push ax   
        inc cl    
        jmp chiatiep 
    
    inso:
        push ax   
        inc cl    
        mov ch,0 
        
    hienso:
        mov ah,2  
        pop dx    
        mov dl,dh 
        add dl,48 
        int 21h
        loop hienso 
        
        mov ah,4ch
        int 21h       
        
     main endp
    end main
        