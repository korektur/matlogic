section .text

global _factorial

_factorial:
mov ecx, [esp + 4]

looper:
cmp ecx, 1
jna ex
sub ecx, 1
mul ecx	
jmp looper
ex:
ret
end