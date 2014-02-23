extern __imp__MessageBoxA@16
extern __imp__ExitProcess@4

%assign MB_ICONINFORMATION 40h

section .text 
global _start

_start:




push MB_ICONINFORMATION
push hello_title
push hello_msg
push 0
call [__imp__MessageBoxA@16]
push 0
call [__imp__ExitProcess@4]
ret

section .data
hello_msg db "hello",0
hello_title db "title", 0
end



