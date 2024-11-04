;de bai: nhap vao 1 so va tinh tong tu 1 den n: 
;khi nhap 1 so (dang ky tu ascii : VD so 0 la 48, no da tang 48 donvi  )
;ta lay so do-48 = so thu dc thuc te, sau do dem so nay di tinh toan
;khi dc ket qua(tong cac so tu 1->n, ta lai +48 de dua no ve kytu ascii va in ra man hinh)
.MODEL SMALL
.STACK 100H
.DATA
tb1 db 'ket qua cua ban la:$' 
tong db 0     
temp db ?
.code 
main proc 
    mov ax,@data
    mov ds,ax
    
    mov ah,1   ;nhap vao 1kytu ,luu o al
    int 21h 
            
  sub al,48   ;dua no ve kytu thuong(lay ascii tru di 48 donvi)
  mov dl,0    ;bien dem bd =0
    lap: 
       add dl,1    ;sau moi vong lap tang bien dem len1
       cmp dl,al   ; ss neu i>n thi thoat
       jg ketqua 
      
       add tong,dl  ;cong bien dem vao bien tong(bd =0)
       jmp lap
    ketqua: 
  
    mov temp,dl    ;gan bien dem vao bien temp
    mov ah,2  
    mov dl,tong     ;do in ra tu thanh dl nen ta gan tong cho dl
    add dl,48         ;chuyen lai ve kytu ascii
    int 21h              ;inra
    
    mov ah,4ch
    int 21h
    main endp 
end main


    
       