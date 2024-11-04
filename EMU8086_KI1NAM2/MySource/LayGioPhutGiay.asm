
 ;Hien thi gio , phut , giay
 ;ham ngat 2ch ,ngat 21h
 ;mov ah,2ch
 ;int 21h
 ;khi ta goi lenh nay,gio phut giay trong may se dc luu vao cac thanh, va ta se lay cac thanh nay gan vao cac bien de hien thi gio phut giay
 ;ch = gio
 ; cl = phut
 ;dh = giay
 ;dl=% giay ( tich tac)
.model small
.stack 100h
.data
    tb1 db 10,13, 'Bay Gio La:$'
    str db '00:00:00$'  ; ban dau chuoi co dang nay: 2 so dau chua gio, 2 so tiep la phut, tiep la giay va tich tac
    
.code
    main proc
        
        mov ax,@data
        mov ds,ax
        
        mov ah,9
        lea dx, tb1  ;in ra tb
        int 21h
        
        mov ah, 2ch   ;goi lenh ngat 2ch
        int 21h
        
        mov si,0   ;cho si=0 de lat no lam ptu dau tien cua chuoi
        mov bl,10  ;bien de lat chia 10
        
        mov ah,0   ;reset thanh ah
        mov al,ch  ;gan gio vao al
        div bl     ;neu gio lon hon 12( tu 12-> 24) thi ta chia 10 , du (phan nguyen cua gio luu o al, phan du luu ah) 
        add al,48  ;chuyen ascii thanh chuso.
        ;VD nhap vao 9 gio => chuyen thanh al =0, ah=9
        ;nhap vao 10 gio => al=1, ah=0
        add ah,48
        mov str[si],al ; gan phan nguyen gio dau tien vao vi tri dau tien cua xau thoi gian
        mov str[si+1],ah ; phan du gio gan vao vi tri tiep (vi tri thu 2) cua xau
        
        mov ah,0    ;lai reset ah, gan phut vao ah, lay ah/10 de lay ra phan nguyen phut va phan du phut nhu tren 
        mov al,cl   ;gan phut vao thanh ah
        div bl
        add al,48   ;lay ra phan nguyen phut va phan du phut, chuyen thanh chuso 
        add ah,48
        mov str[si+3],al  ;luu vao vi tri thu 4 trong xau(do xau bat dau tu 0=> si+3= vitri thu4 
        mov str[si+4],ah  ;ta ko push vao vi tri chua dau 2 cham :
        
        mov ah,0
        mov al,dh
        div bl
        add al,48
        add ah,48
        mov str[si+6],al
        mov str[si+7],al
        
        
        mov ah,9
        lea dx,str ;in gio phut giay tich tac tu chuoi str va in ra
        int 21h
        
        mov ah,4ch
        int 21h
        
main endp
    end main
        