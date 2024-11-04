;NHAP XUAT 1KY TU
;NHAP XUAT 1 CHUOI(tring) :lea si,tenchuoi
;chuyen chu hoa thanh chu thuong(kytu,xau)
;vong lap, re nhanh
;push,pop(trong stack): giup bao ve thanh ghi  
                 
                 ;BAI:INCHUOI DAO NGUOC:
 .model small
 .stack 100h
 .data
 
 crlf db 13,10,'$'  ;khai bao xau xuong dong va lui ra dau dong
 str db 50 dup('$') ;khai bao 1 xau co 50 kytu $ giong nhau de lat ta nhap vao chuoi
 .code 
 main proc
    mov ax,@data
    mov ds,ax
     
    lea si,str  ;gan chuoi str cho thanh si de nhap chuoi se luu vao si
    mov cx,0    ;ta se dem so kytu cua xau de lat in xau ra 
    lap:
    mov ah,1
    int 21h   ;nhap vao tung kytu
    
    cmp al,13   ;ss neu nhap vao kytu xuong dong(so 13) thi nhay den nhan "nhap xong"
    je nhapxong  
    
    mov [si],al ; do 1kytu luu o thanh al=8bit,nen ta ko gan cho thanh si luon ma chi gan cho vi tri i cua thanh si ([si])
  
    push [si]  ;moi gtri si nhap dc ta day vao trong stack(lenh push la day vao stack).sau do [si] se de chua 1 kytu khac dc nhap vao
    inc si   ;nhay den vi tri tiep theo va tiep tuc lap
    inc cx   ;dem cx++
    
    jmp lap 
    nhapxong:
     
    mov ah,9
    lea dx,crlf ;khi ta in ra thi xau dao nguoc se nam de len xau ta nhap, vi the ta in dau xuong dong de nhin dc 2 xau
    int 21h 
    
    hienthi:
    pop dx  ;gan tat ca du lieu ta vua day vao stack tro lai thanh ghi dx sau do in xau ra 
    mov ah,2
    int 21h
    loop hienthi
    
    
    mov ah,4ch
    int 21h
    
    main endp
 end main   
 
 
 ;TA CO THE VIET HAM CON TRONG 1 CTRINH ASSEMBLY:
 .model small
 .stack 100h
 .data
 
 crlf db 13,10,'$'  ;khai bao xau xuong dong va lui ra dau dong
 str db 50 dup('$') ;khai bao 1 xau co 50 kytu $ giong nhau de lat ta nhap vao chuoi
 .code 
 main proc
    mov ax,@data
    mov ds,ax
     
    call nhapxau    ;goi ham va in ra xau vua nhap
    mov ah,9
    lea dx,str
    int 21h
  
    mov ah,4ch
    int 21h
    
    main endp 
 ;viet ham con sau khi ket thuc ham main, goi ham con =lenh "call"
 nhapxau proc ;tenham proc: bat dau ham con   
    lea si,str  ;gan chuoi str cho thanh si de nhap chuoi se luu vao si
    mov cx,0    ;ta se dem so kytu cua xau de lat in xau ra 
    lap:
    mov ah,1
    int 21h   ;nhap vao tung kytu
    
    cmp al,13   ;ss neu nhap vao kytu xuong dong(so 13) thi nhay den nhan "nhap xong"
    je nhapxong  
    
    mov [si],al ; do 1kytu luu o thanh al=8bit,nen ta ko gan cho thanh si luon ma chi gan cho vi tri i cua thanh si ([si])
  
    ;push [si]  ;moi gtri si nhap dc ta day vao trong stack(lenh push la day vao stack).sau do [si] se de chua 1 kytu khac dc nhap vao
    ;=>khi viet bang ham con ko nen dung push vi no se loi. push chi nen dung ow ham chinh
    inc si   ;nhay den vi tri tiep theo va tiep tuc lap
    inc cx   ;dem cx++
    
    jmp lap 
 nhapxong: 
    ret ;return : tra ve kq ctrinh <=> lenh kthuc ham 
    nhapxau endp ;ket thuc ham nhap xau
 end main   
 