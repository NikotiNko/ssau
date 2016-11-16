// Labs4.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include "iostream"
#include "fstream"
#include "windows.h"


using namespace std;

bool divZero(double a, double b) {
	bool f = false;
	__asm{														//st  |  st(1) |  st(2) ... st (7)
		finit; //инициализируем сопроцессор							  |        |
		fld a;	//загружаем а в стек							  a	  |        |
		fld b;   //загружаем b в стек							  b	  |	a      |
		fcom st(1);//сравниваем числа b и a
		fstsw ax;
		sahf; //сохраняем регистр состояний в регистр флагов
		jb Abig;  //если s(0)<s(1) то переходим на эту метку
		ja Asmall; //если s(0)>s(1) то переходим на эту метку
		jmp fend; //переходим в конец
	Asmall:
		ftst;//сравниваем b c нулем, т.к. при a<b необходимо посчитать выражение (b-a)/b
		fstsw ax;
		sahf; //сохраняем регистр состояний в регистр флагов
		jne fend; //если не != 0 то переходим в конец
		inc f; //если == 0, то поднимаем флаг
		jmp fend; //переходим в конец
	Abig://сюда попадаем есть a>b, и т.к. необходимо посчитать выражение b/a+10, проверяем а на 0
		fxch; //меня значения регистров st и st(1) местами			a  |  b     |
		ftst;  //сравниваем а с нулем
		fstsw ax;
		sahf;//сохраняем регистр состояний в регистр флагов
		jne fend; //если не != 0 то переходим в конец
		inc f; //если == 0, то поднимаем флаг
		jmp fend; //переходим в конец
	fend:
	}
	return f;
}

																			 //   ( b/a+10, a>b
double resault(double a, double b) {    //функия вычисляет значение выражения X = < -20, a=b							  
													//                            ( (b-a)/b, a<b
	double ten = 10;
	double equal = -20;
	double nege = -1;
	__asm{															// st   |  st(1)  |   st(2)   ...   st(7)						
			finit; //инициализируем сопроцессор								|		  |
			fld a;	//загружаем а в стек								a	|		  |
			fld b;   //загружаем b в стек								b	|	a	  |
			fcom st(1);//сравниваем числа b и a
			fstsw ax;
			sahf; //сохраняем регистр состояний в регистр флагов
			jb Abig;  //если s(0)<s(1) то переходим на эту метку
			ja Asmall; //если s(0)>s(1) то переходим на эту метку
			fld equal; //сюда попадаем если числа равны.
			jmp fend; //переходим в конец
		Asmall://сюда попадаем если a<b и считаем выражение (b-a)/b, только преобразуем его к виду 1-a/b
			fdivp st(1), st; //											a/b |		   |
			fld nege;												  //-1	|	a/b	   |
			fmulp st(1), st;								//			-a/b|		   |	
			fld1;		//												 1	|	-a/b   |
			faddp st(1), st;										//-a/b+1|          |
			jmp fend; //переходим в конец
		Abig://сюда попадаем если a>b и считаем выражение b/a+10
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
	fin.open("C:\\Users\\vlad\\Desktop\\Labs#6\\условие.txt"); //входной файл
	fout.open("C:\\Users\\vlad\\Desktop\\Labs#6\\ответ.txt"); //выходной файл
	cout << "Вариант №13" << endl;
	cout << "Программа реализовывает функцию вычисления целочисленного выражения на встроенном ассемблере." << endl;
	cout << "Выражение:" << endl;
	cout << "    ( b/a+10, a>b" << endl;
	cout << "X = < -20, a =b" << endl;
	cout << "    ( (b-a)/b" << endl;
	cout << "Все параметры функции 32 битные целые числа." << endl;
	cout << "Числа хранятся в текстовом файле <условие.txt>, который находится в папке Labs#6на Раб. столе." << endl;
	cout << "Ответ записывается в текстовый файл <ответ.txt>, который находится в папке \nLabs#6 на Раб. столе." << endl;
	if (fin.is_open()) { //проверяем существует ли файл
		while (!fin.eof()) { // пока файл не кончился считываем числа 
			fin >> a >> b;
			if (divZero(a,b)) {
				cout << "Для a = " << a << " b = " << b << " результат: деление на 0" << endl;
				fout << "Для a = " << a << " b = " << b << " результат: деление на 0" << endl;
			}
			else {
				double res = resault(a, b);
				cout << "Для a = " << a << " b = " << b << " результат: " << res << endl;
				fout << "Для a = " << a << " b = " << b << " результат: " << res << endl;
			}
		}
	}
	else {
		cout << "Ошибка открытия файла" << endl;
	}
	system("pause");
	return 0;
}

