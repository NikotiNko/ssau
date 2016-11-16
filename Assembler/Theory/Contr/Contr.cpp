// Contr.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include "iostream"

using namespace std;

int _tmain(int argc, _TCHAR* argv[])
{
	int a = 0, b = 0, c = 10;
	__asm{
		xor ebx, ebx;
		mov ecx, 1;
		mov eax, a;
	nachalo:
		cmp eax, 0;
		je konec;
		cdq;
		idiv c;
		test edx, 1;
		jz nachalo; //если четное
		imul edx, ecx;
		add ebx, edx;
		imul ecx, 10;
		cmp eax, 0;
		jne nachalo;
	konec:
		mov b, ebx;
	}
	cout << b << endl;
	system("pause");
	return 0;
}

