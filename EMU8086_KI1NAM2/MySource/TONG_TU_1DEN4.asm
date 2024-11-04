.model small
.stack 100h 
.data
tb1 db 13,10,'nhap vao so n:$'
tb2 db 13,10,'tong cua ban la:$'
n db ?
temp dw 10 ;tao 1 bien tam de lat chia 10
.code
main proc
    mov ax,@data
    mov ds,ax   
    
    
    mov ah,9
    lea dx,tb1
    int 21h 
    
 nhaplai:
    mov ah,1 ;nhap 1 kytu ascii so ,ko phai chu so
    int 21h
    
    cmp al,48
    jl nhaplai ; so sanh neu <48
    cmp al,57    ;neu >57 => no kp chu so, yeu cau ong nhap lai
    jg nhaplai
    sub al,48  
    mov cx,0   ;tao bien cx=0 de lat so sanh cl (luc nay gtri cx=cl=4)
    mov cl,al  ; gan n=4 cho bien cx
         ;i>n: 
    mov ah,9
    lea dx,tb2 ;thong bao kq:
    int 21h   
    
  
    mov ax,0  ;bd tong =0 
    mov bx,1  ;bien dem i=1 
    
lap:
    add ax,bx ; tong+=i
    inc bx   ;i++
    cmp bx,cx  ;i<=n : lap tiep
    jle lap 

    ;VD ta nhap n=4-> tong tu 1->n=10, ta phai tach tung so du ra va push vao stack
    ;sau do pop tung so tu stack ra
    
    mov cx,0 ; dem so luong chu so cua so do
chiatiep:
    mov dx,0 ; phan du cua phep chia moi vong lap moi =0
    div temp ;lay so do chia 10(phep chia 16bit) => nguyen luu o ax,du luu o dx
    add dx,48 ;chuyen so du -> kytu so
    push dx ;day tung kytu vao stack
    inc cx  ;dem xem so do co bao nhieu kytu
    cmp ax,0
    jg chiatiep

intiep:
    pop dx ; day tung kytu trong stack ra
    mov ah,2
    int 21h  ;in ra tung kytu do
    loop intiep  ;cu in den khi bien cx=0
     
    mov ah,4ch
    int 21h
    main endp
end main