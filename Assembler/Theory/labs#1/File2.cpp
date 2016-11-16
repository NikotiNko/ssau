#pragma hdrstop
#pragma argsused

#include <tchar.h>
#include <stdio.h>
#include <Windows.h>

template<typename T1, typename T2>
	// шаблон функции для вычисления выражения
bool Conditional(T1 a, T2 b, double &ans)
{ // возвращает true или false, в зависимости от того прошла ли операция успешно
	// переменная ans нужна для хранения полученного значения
	if (a > b) {
		if (b == -10) {
			return false;
		}
		else {
			ans = a / (b + 10);
			return true;
		}
	}
	if (a == b) {
		ans = -100;
		return true;
	}
	if (a < b) {
		if (b == 0) {
			return false;
		}
		else {
			ans = ((a - 10 * b) / b);
			return true;
		}
	}
}

int JobToMassive(double *arr, int &size, double &b, double &d)
{ // функция обработки массива
	int kol = 0;
	if (b < d) {
		for (int i = 0; i < size; i++) {
			if (arr[i] > 0 && arr[i] >= b && arr[i] <= d) {
				++kol;

			  //	s+=*arr++

			}
		}
	}
	else {
		b = b + d;
		d = b - d;
		b = b - d;
		for (int i = 0; i < size; i++) {
			if (arr[i] > 0 && arr[i] >= b && arr[i] <= d) {
				++kol;
			}
		}
	}

	return kol;
}

bool FileEmpty(FILE *f) { // проверяем пуст ли файл
	fseek(f, 0, SEEK_END);
	int pos = ftell(f);
	if (pos == 0) {
		return false;
	}
	else {
		fseek(f, 0, SEEK_SET);
		return true;
	}

}

int _tmain(int argc, _TCHAR* argv[]) {
	SetConsoleCP(1251);
	// необходимо для корректного отображения русского текста в консоле
	SetConsoleOutputCP(1251);
	printf("Лабараторная работа №1. Выволнил - Плахов Владислав гр. 6208\n");
	printf("Задание №1. Вариант №31:\nДля пар чисел посчитать выражение:\nесли a>b, то записать в файл результат выражения a / (b + 10)\n если a=b, то записать в файл -100\nесли a<b, то записать в файл результат выражения (a - 10 * b) / b\n"
		);
	printf("Задание №2. Вариант №6:\nДан массив. Найти количество положительных элементов массива A={a[i]}, которые удовлетворяют условию: b <= a[i] <= d.\n"
		);
	printf("На раб. столе рассположены ярлыки для файлов с условиями:\nусловие1.txt - числа для первого задания\nусловие2_массив.txt - массив для второго задания\nусловие2_интервал.txt - интервал для второго задания.\n"
		);
	char c;
	printf("Введите любой символ и нажмите Enter\n");
	scanf("%c", &c);
	FILE *condition1, *condition2a, *condition2b, *answer1, *answer2;
	// объявления указателей на файлы
	condition1 = fopen("condition1.txt", "r");
	// открываем файл для чтения условия первого задания
	answer1 = fopen("answer1.txt", "w");
	// открываем файл для записи ответа к первому заданию

	if (FileEmpty(condition1)) {

		while (!feof(condition1)) {
			double ad, bd;
			fscanf(condition1, "%lf", &ad);
			fscanf(condition1, "%lf", &bd); // считываем пары чисел из файла
			double ans;
			if (ad - (int)ad == 0)
			{ // проверка являются ли числа целыми или вещественными
				if (bd - (int)bd == 0) { // оба числа целые
					if (Conditional((int)ad, (int)bd, ans)) {
						fprintf(answer1, "Для чисел %d и %d ответ: %d\n",
							(int)ad, (int)bd, (int)ans);
					}
					else {
						fprintf(answer1,
							"Для чисел %d и %d арифметическое выражение посчитать не возможно(деление на 0)\n",
							(int)ad, (int)bd);
					}
				}
				else { // превое число целое, второе нет
					if (Conditional((int)ad, bd, ans)) {
						fprintf(answer1, "Для чисел %d и %lf ответ: %lf\n",
							(int)ad, bd, ans);
					}
					else {
						fprintf(answer1,
							"Для чисел %d и %lf арифметическое выражение посчитать не возможно(деление на 0)\n",
							(int)ad, bd);
					}
				}
			}
			else {
				if (bd - (int)bd == 0) { // второе числое целое, а первое нет
					if (Conditional(ad, (int)bd, ans)) {
						fprintf(answer1, "Для чисел %lf и %d ответ: %lf\n", ad,
							(int)bd, ans);
					}
					else {
						fprintf(answer1,
							"Для чисел %lf и %d арифметическое выражение посчитать не возможно(деление на 0)\n",
							ad, (int)bd);
					}
				}
				else { // оба числа не целые
					if (Conditional(ad, bd, ans)) {
						fprintf(answer1, "Для чисел %lf и %lf ответ: %lf\n", ad,
							bd, ans);
					}
					else {
						fprintf(answer1,
							"Для чисел %lf и %lf арифметическое выражение посчитать не возможно(деление на 0)\n",
							ad, bd);
					}
				}
			}

		}
	}
	else {
		fprintf(answer1,
		"Файл с заданием пуст, поэтому вы видете это сообщение");
	}
	fclose(condition1);
	fclose(answer1);

	/* -----------------------РАБОТА С МАССИВОМ--------------------- */

	condition2a = fopen("condition2a.txt", "r");
	// открываем файл для чтения массива для второго задания
	condition2b = fopen("condition2b.txt", "r");
	answer2 = fopen("answer2.txt", "w");
	if (FileEmpty(condition2a) && FileEmpty(condition2b)) {

		// открываем файл для записи ответа ко второму заданию
		int size = 0; // храним в переменной размер массива
		double *massive = NULL; // указатель на первый элемент массива
		double numbers; // текущее считанное число
		while (!feof(condition2a)) {
			fscanf(condition2a, "%lf", &numbers);
			size++;
			massive = (double*)realloc(massive, size*sizeof(double));
			// выделяем память под новый элемент массива
			massive[size - 1] = numbers;
			// записываем в массив этот новый элемент
		}

		fprintf(answer2, "Элементы массива\n");
		for (int i = 0; i < size; i++) { // выводим массив в файл ответа
			fprintf(answer2, "%lf ", massive[i]);
		}
		fprintf(answer2, "\n");
		double b, d;
		fscanf(condition2b, "%lf", &b); // считываем интервал из файла условия
		fscanf(condition2b, "%lf", &d); // условие b<=arr[i]<=d
		fprintf(answer2,
			"Количество положительных элементов массива при условии %lf<=a[i]<=%lf\n",
			b, d);
		fprintf(answer2, "%d", JobToMassive(massive, size, b, d));
		// выводим в файл ответа число положительных элементов массива для нашего интервала
		free(massive);
	}
	else {
		fprintf(answer2,
			"Один из файлов с заданием пуст, поэтому вы видете это сообщение");
	}
	printf("Задания выполнены, смотрите результат  работы в файлах на раб. столе.\n"
		);
	char o;
	printf("Введите любой символ и нажмите Enter\n");
	scanf("%c", &o);
	fclose(condition2a);
	fclose(condition2b);
	fclose(answer2);
	return 0;
}
