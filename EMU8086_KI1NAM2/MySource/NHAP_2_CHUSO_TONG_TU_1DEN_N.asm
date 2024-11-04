;nhap vao 1 so co 2 chu so va tinh tong tu 1-> n chu so day
.model small
.stack 100h
.data   
temp dw 10
tb1 db 13,10,'nhap vao n=$'
tb2 db 13,10,'tong tu 1->n =$'
a db 0 ; so ban dau bang 0, sau do ta nhan voi al theo cong thuc: a*10+al de luu 3 so al vua nhap
 ;VD:  a=0
 ; al= 1
 ;a = a*10+al = 1  //khi ta nhap 1 so thi no la hang don vi
 ; al = 2
 ; a= a*10+al = 12 //nhap so t2 thi no la hang chuc, ta lay so hang donvi *10 roi cong vs so t2 vua nhap
 ; al= 5
 ; a = a*10+al =125 //nhap so t3 thi ta dc hang tram  
 
 ;nhu vay ta da nhap dc so 125    
 .code
 main proc
    mov ax,@data
    mov ds,ax
    
    mov ah,9
    lea dx,tb1
    int 21h
    
    mov bl,10 ;lat ta se nhan,chia voi thanh bl=10
nhaptiep:
    mov ah,1
    int 21h
    
    cmp al,13   ;nhap vao tung kytu , neu nhap vao enter thi ta thoat
    je nhapxong
    
    sub al,48   ;chuyen kytu->chuso
    mov bh,al   ;gan tam vao thanh bh
    mov al,a    ;al=0
    mul bl      ;al*10
    add al,bh   ;al+bh(hang don vi)
    mov a,al    ;gan lai so vua nhap tu thanh al cho bien a
    jmp nhaptiep                                           
nhapxong:
    mov ah,9
    lea dx,tb2
    int 21h   
    
    mov ah,0  ;giai phong thanh ax
;ta se tinh tong tu 1-> n:luu kq o thanh ax
    mov al,0  ;bd tong=0
    mov cl,1  ;bien dem i=1
lap:
    add al,cl  ;tong +=i
    inc cl     ;i++
    cmp cl,a   ;so sanh i<=n thi lap tiep
    jle lap 
    
    mov cl,0  ;giai phong bien cl sau khi sd
    mov cx,0  ;dem so luong chu so can in ra
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
       
;trong tinh toan asembly ta han  che dung thanh bien vi ctrinh se bi cham. ta chi nen dung thanh ghi de tinhs toan
