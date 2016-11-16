// Labs5.cpp: ���������� ����� ����� ��� ����������� ����������.
//

#include "stdafx.h"
#include "iostream"
#include "fstream"
#include "windows.h"

using namespace std;

double jobMassive(int *arr, int num){//�������: ����� �������� ����� ������������� � ����� ������������� ��������� ������� A={a[i]}.
	bool f = false;
	__asm{
		mov ecx, num; //���-�� ��������� � �������
		
		mov ebx, arr; //������ �������
		xor esi, esi; //����� ��������� > 0
		xor edx, edx; //����� ��������� < 0
		xor edi, edi; //��� ���� ����� ������ �� �������
		jecxz fend; //���� ����� = 0, �� ������� �� ���������
	beginF:
		mov eax, [ebx + 4 * edi]; //�������� �� ������� ������� � ��������, ������� �������� � �������� edi
		cmp eax, 0;//��������� �����, ��� ������ ��� ������ ����
		jg positive;//��������� �� ����� positive ���� > 0
		js negative;//��������� �� ����� negaitive ���� < 0
		jmp endF; //���� � ������� ����, ��������� �� ��������� �������� �����
	positive:
		add esi, eax; //��������� � ����� ������� ������� �������
		jo overflowError; //��������� ���� ������������
		jmp endF;//��������� �� ��������� �������� �����
	negative:
		add edx, eax; //��������� � ����� ������� ������� �������
		jo overflowError; //��������� ���� ������������
	endF:
		inc edi; //��������� �� ��������� ������� �������
		loop beginF;
	fend:
		mov eax, esi; //� eax ����� ������������� ��������� �������
		sub eax, edx; //�������� ����� ������������� ���������
		jmp fin;
	overflowError:
		inc f;
	fin:
		mov num, eax;

	}
	if (f) {
		return 0.1;
	}
	else {
		return num;
	}
}

int _tmain(int argc, _TCHAR* argv[])
{
	setlocale(LC_ALL, "rus");
	int size;
	int *arr;
	ifstream fin;
	ofstream fout;
	fin.open("C:\\Users\\vlad\\Desktop\\Labs#5\\�������.txt"); //������� ����
	fout.open("C:\\Users\\vlad\\Desktop\\Labs#5\\�����.txt"); //�������� ����
	cout << "������� �13" << endl;
	cout << "��������� ����������� ������� ��������� ��������� ������� ��������� ������� ���������, ��������� � ������ �� ���������� ����������." << endl;
	cout << "�������: ����� �������� ����� ������������� � ����� ������������� ��������� ������� A={a[i]}." << endl;
	cout << "������ �������� � ��������� ����� <�������.txt>, ������� ��������� � �����\nLabs#5 �� ���. �����." << endl;
	cout << "����� ������������ � ��������� ���� <�����.txt>, ������� ��������� � �����\nLabs#5 �� ���. �����." << endl;
	if (fin.is_open()) { //��������� ���������� �� ���� 
		fin >> size; //��������� ������ �������
		arr = new int[size]; //�������� ������ ��� ������
		int *iter = arr;
		while (!fin.eof() && size) { //��������� ������
			fin >> *iter;
			++iter;
		}
		//cout << jobMassive(arr, size);
		double r = jobMassive(arr, size);
		if (r == 0.1) {
			fout << "���������� ��������� �������� ���� ������������� � ������������� ��������� �������, �.�. ��� ���������� ����� �� ���� �������� ������������." << endl;
		}
		else {
			fout << r;
		}
	}
	else {
		cout << "������ �������� �����" << endl;
	}
	system("pause");
	return 0;
}

