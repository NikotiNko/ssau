// Labs5.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include "iostream"
#include "fstream"
#include "windows.h"

using namespace std;

double jobMassive(int *arr, int num){//задание: Найти разность суммы положительных и суммы отрицательных элементов массива A={a[i]}.
	bool f = false;
	__asm{
		mov ecx, num; //кол-во элементов в массиве
		
		mov ebx, arr; //начало массива
		xor esi, esi; //сумма элементов > 0
		xor edx, edx; //сумма элементов < 0
		xor edi, edi; //для того чтобы бегать по массиву
		jecxz fend; //если длина = 0, то выходим из программы
	beginF:
		mov eax, [ebx + 4 * edi]; //переходи на элемент массива с индексом, который хранится в регистре edi
		cmp eax, 0;//проверяем число, оно больше или меньше нуля
		jg positive;//переходим на ветку positive если > 0
		js negative;//переходим на ветку negaitive если < 0
		jmp endF; //если в массиве ноль, переходим на следующую итерацию цикла
	positive:
		add esi, eax; //прибаляем к сумме текущий элемент массива
		jo overflowError; //переходим если переполнение
		jmp endF;//переходим на следующую итерацию цикла
	negative:
		add edx, eax; //прибаляем к сумме текущий элемент массива
		jo overflowError; //переходим если переполнение
	endF:
		inc edi; //переходим на следующий элемент массива
		loop beginF;
	fend:
		mov eax, esi; //в eax сумма положительных элементов массива
		sub eax, edx; //вычитаем сумму отрицательных элементов
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
	fin.open("C:\\Users\\vlad\\Desktop\\Labs#5\\условие.txt"); //входной файл
	fout.open("C:\\Users\\vlad\\Desktop\\Labs#5\\ответ.txt"); //выходной файл
	cout << "Вариант №13" << endl;
	cout << "Программа реализовает функцию обработки элементов массива используя команды сравнения, переходов и циклов на встроенном ассемблере." << endl;
	cout << "Задание: Найти разность суммы положительных и суммы отрицательных элементов массива A={a[i]}." << endl;
	cout << "Массив хранится в текстовом файле <условие.txt>, который находится в папке\nLabs#5 на Раб. столе." << endl;
	cout << "Ответ записывается в текстовый файл <ответ.txt>, который находится в папке\nLabs#5 на Раб. столе." << endl;
	if (fin.is_open()) { //проверяем существует ли файл 
		fin >> size; //считываем размер массива
		arr = new int[size]; //выделяем память под массив
		int *iter = arr;
		while (!fin.eof() && size) { //считываем массив
			fin >> *iter;
			++iter;
		}
		//cout << jobMassive(arr, size);
		double r = jobMassive(arr, size);
		if (r == 0.1) {
			fout << "Невозможно посчитать разность сумм положительных и отрицательных элементов массива, т.к. при вычислении одной из сумм возникло переполнение." << endl;
		}
		else {
			fout << r;
		}
	}
	else {
		cout << "Ошибка открытия файла" << endl;
	}
	system("pause");
	return 0;
}

