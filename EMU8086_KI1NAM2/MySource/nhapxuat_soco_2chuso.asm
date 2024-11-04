;o bai tinh tong tu 1-> n (ta chi dc nhap n<4  ) do khi nhap4 ->tong tu 1->4=10 (bang ma ascii chi nhap dc 1 chu so) nen ta phai nhap 2 chu so vao ghep thanh 1 so
; ta dung den phep nhan va phep chia :  

;ta chia so do cho 10 khi nhap vao : tuc la khi nhap vao 1 so,ta chia 10 de no tach thanh hang chuc va hang don vi(do he thap phan dua tren he 10)
;1 chu so co the bieu dien duoi dang AB, A la so hang chuc va B la so hang don vi   
;VD: neu ng dung nhap vao so 42, se phai nhap 2 so 4 va 2 
; khi chia 10 kq se la:
;4/10 =4(hang chuc) 
;2/10 =2(hang donvi)  (luu o al)

;KHI XUAT SO CO 2 CHU SO: (do bang ma ascii chi co tu 0->9 nen muon xuat 2 so ta xuat tung chu so )
;ta dat 1 bien a=0
;xuat ra hang don vi: a*0+al
;xuat ra hang chuc  : a*10+al


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
        lea dx,s1  ; in ra tb
        int 21h 
        
        mov bl,10  ;gan 10 cho bl de lat ta 
        
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
          
           
;lenh push, pop (ngan xep: chi AD dc du lieu 16bit)
