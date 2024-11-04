;NHAP VAO SO CO 3 CHU SO SAU DO IN RA:
;DUNG PUSH POP:(STACK)
;khi nhap vao stack ta dung push de nhap tung ky tu va day vao stack

 .model small
 .stack 100h
 .data

 tb db 'nhap vao so n:$'  
 tb1 db 13,10, 'so vua nhap :$'  
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
    lea dx,tb ;lenh yeu cau nhap vao 1 so
    int 21h
    
    mov bl,10 ;gan thanh bl=10 de lat chia cho bl
nhaptiep:    
    mov ah,1 ;nhap vao tung kytu 
    int 21h  
    
    cmp al,13 ;nhap vao tung kytu,ktra neu nhap vao \n thi ta thoat
    je nhapxong 
    
    sub al,48 ;neu ko,tru di 48 de chuyen 'kytuso' thanh 'chuso'
    mov bh,al ;gan chu so vua nhap vao bh
    mov al,a  ;thanh al de chua bien a => al=0
    mul bl    ;lay al*10
    add al,bh ;al*10+so vua nhap vao al
    mov a,al  ;ganlai phep toan vao bien a
    jmp nhaptiep
nhapxong:
    mov ah,9
    lea dx,tb1 ;in ra thong bao nhap xong
    int 21h
    
    mov cl,0 ;tao 1 bien dem so kytu cua so do
    mov al,a ;gan so vua nhap vao thanh al: al=125

chiatiep:
    mov ah,0 ;lenh duoi khi ta"push ax" ta chi push so tu al (do kytu nhap vao dc luu o al,
    ;con ah vua khoi tao ah=9 de in, ta reset ah=0 de tranh loi khi push  vao stack) 
    div bl   ;al chia nguyen de lay ra tung chu so tu chu so to 
    cmp al,0 ;den khi =0 thi dung va in so ra
    je inso
    
    push ax  ;moi kytu vua lay ra ta se day vao stack
    inc cl   ;tang bien dem++, tinh so kytu so vua nhap
    jmp chiatiep ;lap den khi nhet het cac so cua chuso do vao stack
    
    inso:
    push ax 
    inc cl;cu moi so day vao stack ta se dem xem co bao nhieu chu so da dc day vao
    mov ch,0 ;do so chu so la phan nguyen dc luu o cl, ta gan ch=0 de ko anh huong den cx=> cx=cl
hienso:    
    mov ah,2
    pop dx ;in ra 1 kytu tu thanh dx
    mov dl,dh
    add dl,48
    int 21h
    loop hienso ;no cu giam den khi cl=0
    
    mov ah,4ch
    int 21h
    
    main endp
 end main   
 
 ;baitap: nhap vao so n co 3 chu so, tinh tong tu 1->n va in ra 