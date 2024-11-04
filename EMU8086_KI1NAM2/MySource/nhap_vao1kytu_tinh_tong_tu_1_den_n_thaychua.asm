.model small
.stack 100h
.data

tb1 db  'nhap n:$'
tb2 db 13,10,'ketqua cua ban la:$'
tong db 0
temp db ?

.code 
main proc
    mov ax,@data
    mov ds,ax
    
    mov ah,9
    lea dx,tb1   ;in ra tb1 "nhap vao n:"
    int 21h
    
    mov ah,1     ;nhap 1 kytu,luu o al
    int 21h
    sub al,48     ;chuyen no tu ascii thanh so bth
    mov bl,al     ;gan sang thanh bl de lat al ta dung tinh toan=> bl=so n nhapvao
    
    mov ah,9
    lea dx,tb2    ;in ra thong bao ket qua
    int 21h
    
    mov al,0      ;ban dau tong=0
    mov cl,1      ; bien i=1

congtiep:  

    add al,cl    ;cho tong +=i
    add cl,1      ;i++
    cmp cl,bl     ;ss neu i<=n thi cong tiep i vao bien tong
    jle congtiep
    
    mov ah,2
    mov dl,al
    add dl,48    ;chuyen kq ve kytu ascii 
    int 21h
    
    mov ah,4ch
    int 21h
    
    main endp
end main
