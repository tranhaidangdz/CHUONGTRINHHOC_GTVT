;nhap vao so n va tinh tong tu 1->n
.model small
.stack 100h 
.data
tb1 db 13,10,'nhap vao so n:$'  
tb2 db 13,10 ,'tong tu 1 den n:$'
tong db 0
temp db ? ;tao 1 bien tam chua co gtri
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
    ;ascii : so 0 =48
           ; so 9 =57
    ;=> ong phai -48 de lay ra chu so
    ;neu nhap TM, thi ta chuyen kytu do thanh chu so(tru di 48)
    sub al,48 ;<=> al-48  
    
    ;tinh tong tu 1->n :khai bao bien i =1 ;i++; dk: i<=n   
    ;tong = tong +i 
    ;jmp : vong lap vo han <=> while(true) , den khi gap dk thoat
    ;loop: vong lap(biet trc so lan lap)  
    
    mov bl,1  ; i=1
    lap:
    add tong,bl ; tong+=i
    add bl,1   ;i++
    cmp bl,al  ;i<=n : lap tiep
    jle lap 
    ;i>n:
    mov ah,9
    lea dx,tb2 ;thong bao kq:
    int 21h 
    
    mov ah,2  
    mov dl,tong ;gan tong vao thanh dl  
    add dl,48  ; chuyen chu so ve kytu ascii roi moi in ra dc 
    int 21h  ;in ra tong
     
     
    mov ah,4ch
    int 21h
    
    main endp
end main