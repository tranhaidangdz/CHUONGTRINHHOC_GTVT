; NHAP VAO 1 CHUOI SO, NHAP VAO 0 THI KET THUC. DEM SO CHU SO CHAN CO TRONG DO
.model small
.stack 100h
.data
tb1 db 13,10,'nhap vao so n:$'
tb2 db 13,10,'so vua nhap la:$'
tb3 db 13,10,'so chu so chan co trong n la:$'
str db 50 dup('$')

.code
main proc
    mov ax,@data
    mov ds,ax
    
    mov ah,9
    lea dx,tb1  ;in tb nhap 1 so 
    int 21h  
    
    mov si,0 ;bat dau tu ptu dau cua thanh si
    
    mov cl,0    ;bien dem bd =0
    mov bx,2    ;bien bx=2 de lat ta :2 ktra so chan 
nhaptiep:    
    mov ah,1    ;nhap vao tung kytu
    int 21h
    mov ah,0 
    mov dx,0
    
    cmp al,'0' ;ktra neu nhap vao so 0 thi thoat
    je thoat   
    
    mov str[si],al  ;gan so do vao ptu dau chuoi si de lat in ra 
    inc si  ;tang vitri de nhap tiep vao vitri sau do 
    
    sub ax,48  ;neu ko thi chuyen no thanh chuso 
    div bx     ;chia 2(phan nguyen luu ax,du o dx)
    cmp dx,0   ;neu so du=0 =>so chan
    je sochan
     
   jmp nhaptiep

 sochan:
    inc cl     ;tang bien dem ++
    jmp nhaptiep    
thoat:

    mov ah,9
    lea dx,tb2  ;tb so vua nhap la:
    int 21h 
    
    lea dx,str   ;in ra so vua nhap luu o thanh si
    int 21h 
    
    lea dx,tb3  ; in tb so chu so chan
    int 21h   
    
    mov dx,0
    mov ch,0
    
    mov ah,2
    mov dl,cl 
    add dl,48
    int 21h
    
    mov ah,4ch
    int 21h 
    
    main endp
end main
    
    