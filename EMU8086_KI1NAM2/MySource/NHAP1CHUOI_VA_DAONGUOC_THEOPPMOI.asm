.model small
.stack 100h
.data
tb1 db 13,10,'nhap vao 1 chuoi:$'
tb2 db 13,10,'chuoi vua nhap la:$'
tb3 db 13,10, 'chuoi sau khi dao nguoc:$'
str db 50 dup('$') 
daonguoc db 50 dup('$')
.code 
main proc
    mov ax,@data
    mov ds,ax                    
    
    mov si,0  ;gan vi tri dau tien=0 cho vitri si
    
    mov ah,9
    lea dx,tb1
    int 21h
    
    mov cx,0 ;bien dem
nhaptiep:    
    mov ah,1
    int 21h   ;nhap vao tung kytu  
    mov ah,0
    
    cmp al,13
    je enter 
    
    mov str[si],al ;vi tri dau tien cua chuoi str gan =al
    inc si ;tang vi tri trong chuoi,nhay den vi tri tiep theo 
    
   push ax ;do ta mov ah=0 roi nen push 
    inc cx ;dem so kytu da push 
    jmp nhaptiep
    
enter: 
    mov ah,9
    lea dx,tb2
    int 21h
    
    lea dx,str ;in ra chuoi vua nhap vao 
    int 21h 
    
    lea dx,tb3
    int 21h
    
    mov bx,0  ;tao 1 bien dem de dem so kytu gan vao xau dao nguoc
layra:
    pop ax    ;lay ca ax ra, nhung mk chi can al(do ban dau nhap vao no luu o al)
    mov ah,0
    mov daonguoc[bx],al ;gan kytu vua lay ra (ax=al) tu stack vao tung ptu cua xau dao nguoc
    inc bx   ; tang vi tri tiep theo cua thanh bx de ta push vao
    loop layra ;cu pop ra den khi bien dem cx tren =0
        
    mov ah,9
    lea dx,daonguoc
    int 21h
                  
mov ah,4ch
int 21h

main endp
end main
    
    