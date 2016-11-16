// Labs4.cpp: ���������� ����� ����� ��� ����������� ����������.
//

#include "stdafx.h"
#include "iostream"
#include "fstream"
#include "windows.h"


using namespace std;

bool divZero(double a, double b) {
	bool f = false;
	__asm{														//st  |  st(1) |  st(2) ... st (7)
		finit; //�������������� �����������							  |        |
		fld a;	//��������� � � ����							  a	  |        |
		fld b;   //��������� b � ����							  b	  |	a      |
		fcom st(1);//���������� ����� b � a
		fstsw ax;
		sahf; //��������� ������� ��������� � ������� ������
		jb Abig;  //���� s(0)<s(1) �� ��������� �� ��� �����
		ja Asmall; //���� s(0)>s(1) �� ��������� �� ��� �����
		jmp fend; //��������� � �����
	Asmall:
		ftst;//���������� b c �����, �.�. ��� a<b ���������� ��������� ��������� (b-a)/b
		fstsw ax;
		sahf; //��������� ������� ��������� � ������� ������
		jne fend; //���� �� != 0 �� ��������� � �����
		inc f; //���� == 0, �� ��������� ����
		jmp fend; //��������� � �����
	Abig://���� �������� ���� a>b, � �.�. ���������� ��������� ��������� b/a+10, ��������� � �� 0
		fxch; //���� �������� ��������� st � st(1) �������			a  |  b     |
		ftst;  //���������� � � �����
		fstsw ax;
		sahf;//��������� ������� ��������� � ������� ������
		jne fend; //���� �� != 0 �� ��������� � �����
		inc f; //���� == 0, �� ��������� ����
		jmp fend; //��������� � �����
	fend:
	}
	return f;
}

																			 //   ( b/a+10, a>b
double resault(double a, double b) {    //������ ��������� �������� ��������� X = < -20, a=b							  
													//                            ( (b-a)/b, a<b
	double ten = 10;
	double equal = -20;
	double nege = -1;
	__asm{															// st   |  st(1)  |   st(2)   ...   st(7)						
			finit; //�������������� �����������								|		  |
			fld a;	//��������� � � ����								a	|		  |
			fld b;   //��������� b � ����								b	|	a	  |
			fcom st(1);//���������� ����� b � a
			fstsw ax;
			sahf; //��������� ������� ��������� � ������� ������
			jb Abig;  //���� s(0)<s(1) �� ��������� �� ��� �����
			ja Asmall; //���� s(0)>s(1) �� ��������� �� ��� �����
			fld equal; //���� �������� ���� ����� �����.
			jmp fend; //��������� � �����
		Asmall://���� �������� ���� a<b � ������� ��������� (b-a)/b, ������ ����������� ��� � ���� 1-a/b
			fdivp st(1), st; //											a/b |		   |
			fld nege;												  //-1	|	a/b	   |
			fmulp st(1), st;								//			-a/b|		   |	
			fld1;		//												 1	|	-a/b   |
			faddp st(1), st;										//-a/b+1|          |
			jmp fend; //��������� � �����
		Abig://���� �������� ���� a>b � ������� ��������� b/a+10
			fdivrp st(1), st; //   										b/a |          |
			fld ten;												//   10 |    b/a   |
			faddp st(1), st;										//10+b/a|          |
		fend:
			; fstp a;
		}
	//return a;
}



int _tmain(int argc, _TCHAR* argv[])
{
	setlocale(LC_ALL, "rus");
	double a, b;
	ifstream fin;
	ofstream fout;
	fin.open("C:\\Users\\vlad\\Desktop\\Labs#6\\�������.txt"); //������� ����
	fout.open("C:\\Users\\vlad\\Desktop\\Labs#6\\�����.txt"); //�������� ����
	cout << "������� �13" << endl;
	cout << "��������� ������������� ������� ���������� �������������� ��������� �� ���������� ����������." << endl;
	cout << "���������:" << endl;
	cout << "    ( b/a+10, a>b" << endl;
	cout << "X = < -20, a =b" << endl;
	cout << "    ( (b-a)/b" << endl;
	cout << "��� ��������� ������� 32 ������ ����� �����." << endl;
	cout << "����� �������� � ��������� ����� <�������.txt>, ������� ��������� � ����� Labs#6�� ���. �����." << endl;
	cout << "����� ������������ � ��������� ���� <�����.txt>, ������� ��������� � ����� \nLabs#6 �� ���. �����." << endl;
	if (fin.is_open()) { //��������� ���������� �� ����
		while (!fin.eof()) { // ���� ���� �� �������� ��������� ����� 
			fin >> a >> b;
			if (divZero(a,b)) {
				cout << "��� a = " << a << " b = " << b << " ���������: ������� �� 0" << endl;
				fout << "��� a = " << a << " b = " << b << " ���������: ������� �� 0" << endl;
			}
			else {
				double res = resault(a, b);
				cout << "��� a = " << a << " b = " << b << " ���������: " << res << endl;
				fout << "��� a = " << a << " b = " << b << " ���������: " << res << endl;
			}
		}
	}
	else {
		cout << "������ �������� �����" << endl;
	}
	system("pause");
	return 0;
}

