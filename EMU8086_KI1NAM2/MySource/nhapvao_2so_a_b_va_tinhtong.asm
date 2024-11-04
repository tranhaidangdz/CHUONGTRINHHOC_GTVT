; nhap 2 so a,b va tinh tong a+b, in ra man hinh  
.model small
.stack 100h
.data
tb1 db 13,10,'nhap vao a:$'
tb db 13,10,'phai nhap vao 1 so nguyen !$'
tb2 db 13,10,'nhap vao b:$'
tb3 db 13,10,'tong cua 2 so la:$'

a db ?
b db ?
tong db 0

.code 
main proc
    mov ax,@data
    mov ds,ax
    
    jmp input1 ;no nhay den bc nhap vao 1 so(tranh in ra tb nhap lai khi chua can thiet)
nhaplai:
    mov ah,9
    lea dx,tb ;tb nhap lai so do
    int 21h 
   
input1:
    mov ah,9    
    lea dx,tb1 ; xuat tb yeu cau nhap vao so a:
    int 21h
    
    mov ah,1
    int 21h
    
    cmp al,48 
    jl nhaplai ;neu ta nhap so<0 hoac so>9 thi ta nhap lai
    cmp al,57
    jg nhaplai
    
    sub al,48 ;neu nhap so TM thi ta chyen kytu so->chu so va luu vao bien a
    mov a,al
   
jmp input2
 nhaplai2:
    mov ah,9
    lea dx,tb ;tb nhap lai so do
    int 21h 
   
input2:
    mov ah,9    
    lea dx,tb2 ; xuat tb yeu cau nhap vao so b:
    int 21h
    
    mov ah,1
    int 21h
    
    cmp al,48 
    jl nhaplai ;neu ta nhap so<0 hoac so>9 thi ta nhap lai
    cmp al,57
    jg nhaplai
    
    sub al,48 ;neu nhap so TM thi ta chyen kytu so->chu so va luu vao bien a
    mov b,al 
     
 ;=========================================================
     mov ah,9
     lea dx,tb3 ;in ra thong bao tinh tong:
     int 21h 
     
    ; add a,b  ; lay a= a+b. nhung ta ko dc cong truc tiep bien ntn, phai gan vao thanh ghi sau do cong thanh ghi
    mov al,a
    mov bh,b
    add al,bh ; al=al+bh
    
    mov tong,al  ;gan al vao bien tong
    add tong,48  ;chuyen so do ve kytu ascii va in ra
    
    mov ah,2
    mov dl,tong  ;in ra ket qua bien tong. tuy nhien cach nay chi in ra dc khi tong=1so. ney 
                 ;  kq tong>=2 so thi ko in dc(do bang ascii chi co tu0->9)
    int 21h
    
    mov ah,4ch
    int 21h
                   
 main endp
end main

    
