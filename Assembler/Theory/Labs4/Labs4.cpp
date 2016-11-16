// Labs4.cpp: ���������� ����� ����� ��� ����������� ����������.
//

#include "stdafx.h"
#include "iostream"
#include "fstream"
#include "windows.h"


using namespace std;
																								//   ( b/a+10, a>b
bool zero(int a, int b) { //������� ���������, �������� �� ������� �� 0 ��� ���������� ��������� X = < -20, a=b
	__asm{//������ ���������� true ���� �������� ������� �� 0 � false ���� ���					     ( (b-a)/b, a<b
		mov eax, a;  //eax = a
		mov ebx, b;  // ebx = b
		cmp eax, ebx;  // ���������� �������� ��������� eax, ebx
		jno NextStep;  //���������� ��� �������� ���� �� �� ������������ ��� ���������, ������ ��� a=-10, b=2147483640
		//���� �� ���� ���� ������, �.�. �������� ���������, ���� ���� �� ���������� �� ������ 
		cmp ebx, eax;
		jg Asmall;  //�.�. �� �������� ������ ebx � eax �� �������� ��������������� ��������� ������� ���������
		//������������� �������� ��������������� ��������
		//��� jmp ������ ��� ����� ���� �� �������� js Abig; jo Abig; ������ ������� �� jmp Abig ��� ��� �������
		jmp Abig; //���� ����� ���� ������������, ������ � ��� ����� �������� a = 5, b = -2147483648
	NextStep:
		jg Abig;  //���� �������� eax ������, �� ��������� �� ����� Abig
		js Asmall; //���� ������, ��������� �� ����� Asmall
	Abig:
		or eax, eax; //��� ��������� ������� �� 0
		jz errorZero; //���� ��������� 0 �� ��������� �� ����� errorZero
		jmp fend; //���� �������� ���� a != 0
	Asmall:
		or ebx, ebx; //��� ��������� ������� �� 0
		jz errorZero; //���� ��������� 0 �� ��������� �� ����� errorZero
		jmp fend;  //���� �������� ���� b != 0
	errorZero:
		mov a, 0; 
		jmp fine;
	fend:
		mov a, 1;
		fine:
	}
	return !a;
}

																	   //   ( b/a+10, a>b
double resault(int a, int b) {    //������ ��������� �������� ��������� X = < -20, a=b							  
	bool of = false;		//���� ������������                             ( (b-a)/b, a<b
	if (!zero(a, b)) {
		__asm{
			mov eax, a;  //eax = a
			mov ebx, b;  // ebx = b
			cmp eax, ebx;  // ���������� �������� ��������� eax, ebx
			jno NextStep;  //���������� ��� �������� ���� �� �� ������������ ��� ���������, ������ ��� a=-10, b=2147483640
			//���� �� ���� ���� ������, �.�. �������� ���������, ���� ���� �� ���������� �� ������ 
			cmp ebx, eax;
			jg Asmall;  //�.�. �� �������� ������ ebx � eax �� �������� ��������������� ��������� ������� ���������
			//��� jmp ������ ��� ����� ���� �� �������� js Abig; jo Abig; ������ ������� �� jmp Abig ��� ��� �������
			jmp Abig; //���� ����� ���� ������������, ������ � ��� ����� �������� a = 5, b = -2147483648
		NextStep:
			jg Abig;  //���� �������� eax ������, �� ��������� �� ����� Abig
			js Asmall; //���� ������, ��������� �� ����� Asmall
			mov eax, -20; //���� �� ��������, ���� �������� ��������� eax � ebx ���������
			jmp fend; //��������� � �����
		Abig://���� �������� ���� a>b � ������� ��������� b/a+10
			mov eax, ebx;  //eax = b
			cdq;
			idiv a; //eax = b/a
			add eax, 10; //eax = b/a +10
			jo errorOverflow; //���� ���� ������������ ��������� �� �������������� �����
			jmp fend; //��������� � �����
		Asmall:// ���� �������� ���� b>a � ������� ��������� (b-a)/b, ����������� ��� � ���� 1-a\b
			cdq;
			idiv ebx; //eax = a/b
			neg eax;
			inc eax;
			jo errorOverflow;
			jmp fend; //��������� � �����
		errorOverflow:
			inc of; //������������� ���� ������������
		fend:
			mov a, eax;
		}
	}
	else {
		return 0.1; //��� ������ ���� �������� ������� �� 0;
	}
	if (of) {
		return 0.2; //���� ��� ���������� ���� ������������, ��� ������ ����� 0.2
	}
	return a;
}



int _tmain(int argc, _TCHAR* argv[])
{
	setlocale(LC_ALL, "rus");
	int a, b;
	ifstream fin; 
	ofstream fout;
	fin.open("C:\\Users\\vlad\\Desktop\\Labs#4\\�������.txt"); //������� ����
	fout.open("C:\\Users\\vlad\\Desktop\\Labs#4\\�����.txt"); //�������� ����
	cout << "������� �13" << endl;
	cout << "��������� ������������� ������� ���������� �������������� ��������� �� ���������� ����������." << endl;
	cout << "���������:" << endl;
	cout << "    ( b/a+10, a>b" << endl;
	cout << "X = < -20, a =b" << endl;
	cout << "    ( (b-a)/b" << endl;
	cout << "��� ��������� ������� 32 ������ ����� �����." << endl;
	cout << "����� �������� � ��������� ����� <�������.txt>, ������� ��������� � ����� Labs#4�� ���. �����." << endl;
	cout << "����� ������������ � ��������� ���� <�����.txt>, ������� ��������� � ����� \nLabs#4 �� ���. �����." << endl;
	if (fin.is_open()) { //��������� ���������� �� ����
		while (!fin.eof()) { // ���� ���� �� �������� ��������� ����� 
			fin >> a >> b;
			double res = resault(a, b); 
			cout << "��� a = " << a << " b = " << b << " ���������: ";
			fout << "��� a = " << a << " b = " << b << " ���������: ";
			if (res == 0.1) {
				cout << "������� �� 0" << endl;
				fout << "������� �� 0" << endl;
			}
			else {
				if (res == 0.2) {
					cout << "������������" << endl;
					fout << "������������" << endl;
				}
				else {
					cout << (int)res << endl;
					fout << (int)res << endl;
				}
			}
			
		}
	}
	else {
		cout << "������ �������� �����" << endl;
	}
	system("pause");
	return 0;
}

