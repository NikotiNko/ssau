// Labs4.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include "iostream"
#include "fstream"
#include "windows.h"


using namespace std;
																								//   ( b/a+10, a>b
bool zero(int a, int b) { //функция проверяет, возможно ли деление на 0 при вычислении выражения X = < -20, a=b
	__asm{//функци возвращает true если возможно деление на 0 и false если нет					     ( (b-a)/b, a<b
		mov eax, a;  //eax = a
		mov ebx, b;  // ebx = b
		cmp eax, ebx;  // сравниваем значения регистров eax, ebx
		jno NextStep;  //необходимо для проверки было ло ли переполнение при сравнении, пример при a=-10, b=2147483640
		//если не было идем дальше, т.к. сравнили правильно, если было то сравниваем по новому 
		cmp ebx, eax;
		jg Asmall;  //т.к. мы сравнили теперь ebx с eax то получаем противоположный результат первому сравнению
		//соответсвенно получаем противоположные переходы
		//тут jmp потому что можно было бы написать js Abig; jo Abig; просто заменим на jmp Abig эти две команды
		jmp Abig; //если снова было переполнение, значит у нас числа например a = 5, b = -2147483648
	NextStep:
		jg Abig;  //если значение eax больше, то переходим на метку Abig
		js Asmall; //если меньше, переходим на метку Asmall
	Abig:
		or eax, eax; //тут проверяем деление на 0
		jz errorZero; //если получился 0 то переходим на метку errorZero
		jmp fend; //сюда попадаем если a != 0
	Asmall:
		or ebx, ebx; //тут проверяем деление на 0
		jz errorZero; //если получился 0 то переходим на метку errorZero
		jmp fend;  //сюда попадаем если b != 0
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
double resault(int a, int b) {    //функия вычисляет значение выражения X = < -20, a=b							  
	bool of = false;		//флаг переполнения                             ( (b-a)/b, a<b
	if (!zero(a, b)) {
		__asm{
			mov eax, a;  //eax = a
			mov ebx, b;  // ebx = b
			cmp eax, ebx;  // сравниваем значения регистров eax, ebx
			jno NextStep;  //необходимо для проверки было ло ли переполнение при сравнении, пример при a=-10, b=2147483640
			//если не было идем дальше, т.к. сравнили правильно, если было то сравниваем по новому 
			cmp ebx, eax;
			jg Asmall;  //т.к. мы сравнили теперь ebx с eax то получаем противоположный результат первому сравнению
			//тут jmp потому что можно было бы написать js Abig; jo Abig; просто заменим на jmp Abig эти две команды
			jmp Abig; //если снова было переполнение, значит у нас числа например a = 5, b = -2147483648
		NextStep:
			jg Abig;  //если значение eax больше, то переходим на метку Abig
			js Asmall; //если меньше, переходим на метку Asmall
			mov eax, -20; //сюда мы попадаем, если значения регистров eax и ebx совпадает
			jmp fend; //переходим в конец
		Abig://сюда попадаем если a>b и считаем выражение b/a+10
			mov eax, ebx;  //eax = b
			cdq;
			idiv a; //eax = b/a
			add eax, 10; //eax = b/a +10
			jo errorOverflow; //если было переполнение переходим на соответсвующую метку
			jmp fend; //переходим в конец
		Asmall:// сюда попадаем если b>a и считаем выражение (b-a)/b, преобразуем его к виду 1-a\b
			cdq;
			idiv ebx; //eax = a/b
			neg eax;
			inc eax;
			jo errorOverflow;
			jmp fend; //переходим в конец
		errorOverflow:
			inc of; //устанавливаем флаг переполнения
		fend:
			mov a, eax;
		}
	}
	else {
		return 0.1; //код ошибки если возможно деление на 0;
	}
	if (of) {
		return 0.2; //если был установлен флаг переполнения, код ошибки число 0.2
	}
	return a;
}



int _tmain(int argc, _TCHAR* argv[])
{
	setlocale(LC_ALL, "rus");
	int a, b;
	ifstream fin; 
	ofstream fout;
	fin.open("C:\\Users\\vlad\\Desktop\\Labs#4\\условие.txt"); //входной файл
	fout.open("C:\\Users\\vlad\\Desktop\\Labs#4\\ответ.txt"); //выходной файл
	cout << "Вариант №13" << endl;
	cout << "Программа реализовывает функцию вычисления целочисленного выражения на встроенном ассемблере." << endl;
	cout << "Выражение:" << endl;
	cout << "    ( b/a+10, a>b" << endl;
	cout << "X = < -20, a =b" << endl;
	cout << "    ( (b-a)/b" << endl;
	cout << "Все параметры функции 32 битные целые числа." << endl;
	cout << "Числа хранятся в текстовом файле <условие.txt>, который находится в папке Labs#4на Раб. столе." << endl;
	cout << "Ответ записывается в текстовый файл <ответ.txt>, который находится в папке \nLabs#4 на Раб. столе." << endl;
	if (fin.is_open()) { //проверяем существует ли файл
		while (!fin.eof()) { // пока файл не кончился считываем числа 
			fin >> a >> b;
			double res = resault(a, b); 
			cout << "Для a = " << a << " b = " << b << " результат: ";
			fout << "Для a = " << a << " b = " << b << " результат: ";
			if (res == 0.1) {
				cout << "деление на 0" << endl;
				fout << "деление на 0" << endl;
			}
			else {
				if (res == 0.2) {
					cout << "переполнение" << endl;
					fout << "переполнение" << endl;
				}
				else {
					cout << (int)res << endl;
					fout << (int)res << endl;
				}
			}
			
		}
	}
	else {
		cout << "Ошибка открытия файла" << endl;
	}
	system("pause");
	return 0;
}

