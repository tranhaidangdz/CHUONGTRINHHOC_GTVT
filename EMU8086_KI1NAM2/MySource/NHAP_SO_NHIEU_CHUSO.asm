;NHAP 1 SO BAT KY(NHIEU CHU SO VA IN RA)
.MODEL SMALL
.STACK 100H
.DATA 
tb1 db 13,10, 'nhap so co n chuso:$'
tb2 db 13,10,'so vua nhap la:$'
a dw 0 


.code 
main proc
       mov ax,@data
       mov ds,ax  
       
       mov ah,9
       lea dx,tb1
       int 21h
       
       mov bx,10  ;bien dung de chia 10
       mov cx,0   ;de lat ta luu al vao cl ko bi loi
nhaptiep:
       mov ah,1
       int 21h
       
       cmp al,13
       je nhapxong
       

       sub al,48
       mov cl,al ;cat so vua nhap vao thanh cl
       mov ax,a  ;ax=0
       mul bx    ;ax*10
       add ax,cx ;ax+cx =ax+ cl = al vua cat vao
       mov a,ax
       
       jmp nhaptiep

nhapxong:
       mov ah,9
       lea dx,tb2
       int 21h
       
       mov bx,10
       mov ax,a  ;gan so vua nhap vao ax
       mov cx,0  ;bien dem cx =0(cx vua bi thay doi nen trc khi sd ta reset lai)
chiatiep:
        mov dx,0 ;phan du sau moi vong lap=0
        div bx   ;lay dxax/10
        cmp ax,0 ;chia den khi ax=0 thi thoat 
        je kt    
     
       push dx     ;day vao thanh px 
       inc cx      ;tang bien dem len 1
       jmp chiatiep 
       
kt:
        push dx   ;day so du vao dx
        inc cx
        
intiep:
        
        pop dx  ;day tung so trong stack ra
        add dl,48  
         mov ah,2 
        int 21h   
        
        loop intiep     
       mov ah,4ch
       int 21h
      
 
 main endp
end main

       
       