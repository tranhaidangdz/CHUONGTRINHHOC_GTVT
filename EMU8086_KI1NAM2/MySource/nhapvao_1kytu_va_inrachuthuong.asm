  ;nhap vao 1 kytu, kiem tra xem do la kytu hoa hay thuong
 ;-neu la chu hoa thi in ra luon
 ;-neu la chu thuong thi in ra tu a-> chu do 
 ;chu hoa nam trong khoang tu 65-90,neu no <65 hoac >90 thi no kp chu hoa
 
 .model small
 .stack 100h
 .data
 tb1 db 'nhap vao 1 kytu:$'
 xuongdong db 13,10,'$'
 
  .code 
  main proc
    mov ax,@data
    mov ds,ax
    
    mov ah,9
    lea dx,tb1  ;nhap vao 1 kytu
    int 21h
    
    mov ah,1     ;nhap vao kytu,luu o al
    int 21h
    mov bl,al     ;gan kytu vua nhap vao thanh bl
    
    mov ah,9
    lea dx,xuongdong ;in dau xuong dong
    int 21h 
    
    
    cmp bl,97     ; ss kytu vua nhap voi chu a thuong, neu >= thi chac chan do la chu thuong
    jge chuthuong
    
    mov ah,2
    mov dl,bl     ;neu ko phai chu thuong ta in ra kytu do luon  va ket thuc ctrinh
    int 21h 
    jmp thoat
    
 chuthuong:
 mov dl,97    ;gan chu a thuong cho thanh dl va inra

 inkytu:
 mov ah,2     ;in ra 1kytu tu thanh dl
 int 21h
 inc dl      ;tang dl++
 cmp dl,bl   ;neu dl van <= chu thuong do thi tiep tuc in 
 jle inkytu
 
 thoat:
 mov ah,4ch
 int 21h
 
 main endp
  end main
  