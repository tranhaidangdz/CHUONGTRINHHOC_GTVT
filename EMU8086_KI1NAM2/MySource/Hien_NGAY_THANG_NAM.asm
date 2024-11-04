;hien ngay thang nam, ham 2ah,int 21h
;ket qua: al=thu trong tuan(0=chu nhat, 6=thu 7)
;dl= ngay (1-31)
;dh = thang (1-12)
;cx = nam(1980-2023)
.model small
.stack 100h
.data
    tb1 db 13,10,'hom nay la:$'
    ngay db ?
    thang db ?
    nam dw ?
    cn db 'chu nhat$'
    thu db 'thu $' 
    thux db 'haiba tu namsaubay' ;khi muon in ra chu thu may, ta se in ra 3 kytu trong xau, cu noi tiep nhau nhu vay
    ;VD thu2 =so1, ta in ra 3 kytu dau chuoi
    ;VD thu3 =so2, ta in ra 3 kytu tiep theo(do tu"ba " co 2 chu nen ta de cach ra 1 khoang trang cho du 3
    ;tuong tu thu 4 ta cung cach ra 1 khoang trang nhu thu 3 do tu "tu " chi co 2 kytu 
    
.code 
main proc
    mov ax,@data
    mov ds,ax
    
    mov ah,9
    lea dx,tb1
    int 21h
    
    mov ah,2ah
    int 21h
    ; sau lenh nay cac thanh dc gan : cx=nam,dh=thang , dl=ngay,al=thu
    mov nam, cx
    mov thang,dh
    mov ngay,dl
    
    cmp al,0
    je chunhat
    jmp thu1   ;ktra xem la thu may? 
    
chunhat:
    mov ah,9
    lea dx,cn
    int 21h
    jmp tiep
thu1:
    mov bh,al
    mov ah,9
    lea dx,thu
    int 21h 
    
    mov al,bh
    sub al,1
    
    mov ah,2
    mov dl,bh
    add dl,49  ;tang them 1 don vi nua(do thu 2 no chua so 1, ta cong 1 de chuyen ve so 2)
    int 21h  
tiep: 
    mov ah,2
    mov dl,32
    int 21h
    
    mov al,ngay
    mov ah,0
    mov bl,10
    div bl
    add al,48
    add ah,48
    
    mov bh,ah
    mov ah,2
    mov bl,al
    int 21h
    
    mov dl,bh
    int 21h
    mov dl,'/'
    int 21h 
    
    mov al,thang
    mov ah,0
    div bl
    add al,48
    add ah,48 
    
     mov bh,ah
    mov ah,2
    mov dl,al
    int 21h
    
    mov dl,bh
    int 21h
    mov dl,'/'
    int 21h   
    
    mov ax,nam
    mov cx,4
    mov bx,10
    
 chiatiep:
    mov dx,0
    div bx
    push dx
    loop chiatiep
    mov cx,4
innam:
    mov ah,2
    pop dx
    add dl,48
    int 21h
    loop innam
     
    mov ah,4ch 
    int 21h
    
    main endp
end main
        
    