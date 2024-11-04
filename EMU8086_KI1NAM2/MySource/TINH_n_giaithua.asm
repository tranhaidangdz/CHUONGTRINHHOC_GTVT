;nhap vao 1 so va tinh n giai thua:

.model small
.stack 100h
.data   

chia10 dw 10  ;lat chia so do cho 10 de tach thanh hang chuc va hang don vi
tb1 db 13,10,'nhap vao n =$'
tb2 db 13,10,'n giai thua =$'

.code
main proc
    mov ax,@data
    mov ds,ax     ;nap du lieu , khoi tao thanh ghi ds
    
    mov ah,9
    lea dx,tb1
    int 21h 
    
    mov ah,1  ;nhap 1 kytu
    int 21h
    
    sub al,'0' ;tru di kytu '0' => chuyen kytu so ve chu so
    mov cx,0   ;tao bien cx=0 de lat so sanh cl (luc nay gtri cx=cl=5= n giaithua)
    mov cl,al  ;gan chu so vua nhap vao thanh cl =>cl=5(gsu the)
    
    mov ah,9
    lea dx,tb2 ;tb1 kq phep toan la:
    int 21h  
    
   
   ;tinh giai thua: nhan tu 1->n : 1*2*3*...*n
   mov ax,1 ; ban dau ket qua n giai thua =1
   mov bx,1 ;bd i=1
giaithua:
   mul bx  ; <=> dxax= ax*bx, nhan bang ax (nen bd ta gan gtri cho ax=1 de nhan lan luot)
   inc bx  ;tang bien i++
   cmp bx,cx ;so sanh neu i<=n ( i<5 ) thi tiep tuc tang i, sau do nhan i vao ax
   jle giaithua  
   ;=>sau khi nhan 5giaithua , no tra ve kq 120 luu o ax (so 120 nay o dang chuso, khi in ra ta phai in
   ; no ra dang chuoi hoac tach no ra, in ra tung kytu)

 mov cx,0  ; sau khi da tinh xong 5! ta giai phong bien cx(chua so n) de lat sd
 
 ;cach in ra kq 120: ta dung stack: 
 ;120 => push vao stack 0,1,2 (last in first out: cai nao vao sau se ra trc) 
 ; => pop ra stack 1,2,0

lap:  ;ham nay lay cac kytu so cua 120 va day vao trong ngan xep  

;khi day vao ngan xep ta phai day nguoc so 120: day 0->2->1  (thi lat in ra se la: 0->2->1)
;ta lay so 120 chia nguyen cho 10 (tuc la lay ra tu kytu cuoi->dau, sau do push tung kytu vao stack)
;120/10 = 0 : push 0 vao stack
;12/10 = 2 :push 2 vao stack  
;1/10 = 1: push 1 vao stack : khi ta thuc hienn phep chia, phan nguyen luu thanh al, du luu o ah (hoac ch,bh...) noi chung thanh h luu du
; ma ta can lay so du ra de push vao stack  

    mov dx,0 ; bien dx de luu so du(ta can lay so du) ,cu moi vong lap moi ta cho dx=0
    div chia10 ;tach tung chu so cua 120 ra(lay dxax/10) :ta chia 16 bit=> ax luu nguyen,dx luu du
    add dx,'0' ; chuyen chuso ->kytu so (hoac ta +48)
    push dx    ;day tung so du vao ngan xep: 0->2->1
    inc cx     ;dem so chu so 
    cmp ax,0  ;ss neu a>0 tuc la a van con chu so chua push=> lap tiep
    jg lap 
hienthi:
    pop dx ;day tung kytu du trong stack ra
    mov ah,2
    int 21h
    loop hienthi ;cu lap den khi bien dem cx=0          
     
    mov ah,4ch
    int 21h   
 main endp
end main