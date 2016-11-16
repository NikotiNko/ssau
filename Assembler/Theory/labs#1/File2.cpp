#pragma hdrstop
#pragma argsused

#include <tchar.h>
#include <stdio.h>
#include <Windows.h>

template<typename T1, typename T2>
	// ������ ������� ��� ���������� ���������
bool Conditional(T1 a, T2 b, double &ans)
{ // ���������� true ��� false, � ����������� �� ���� ������ �� �������� �������
	// ���������� ans ����� ��� �������� ����������� ��������
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
{ // ������� ��������� �������
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

bool FileEmpty(FILE *f) { // ��������� ���� �� ����
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
	// ���������� ��� ����������� ����������� �������� ������ � �������
	SetConsoleOutputCP(1251);
	printf("������������ ������ �1. �������� - ������ ��������� ��. 6208\n");
	printf("������� �1. ������� �31:\n��� ��� ����� ��������� ���������:\n���� a>b, �� �������� � ���� ��������� ��������� a / (b + 10)\n ���� a=b, �� �������� � ���� -100\n���� a<b, �� �������� � ���� ��������� ��������� (a - 10 * b) / b\n"
		);
	printf("������� �2. ������� �6:\n��� ������. ����� ���������� ������������� ��������� ������� A={a[i]}, ������� ������������� �������: b <= a[i] <= d.\n"
		);
	printf("�� ���. ����� ������������ ������ ��� ������ � ���������:\n�������1.txt - ����� ��� ������� �������\n�������2_������.txt - ������ ��� ������� �������\n�������2_��������.txt - �������� ��� ������� �������.\n"
		);
	char c;
	printf("������� ����� ������ � ������� Enter\n");
	scanf("%c", &c);
	FILE *condition1, *condition2a, *condition2b, *answer1, *answer2;
	// ���������� ���������� �� �����
	condition1 = fopen("condition1.txt", "r");
	// ��������� ���� ��� ������ ������� ������� �������
	answer1 = fopen("answer1.txt", "w");
	// ��������� ���� ��� ������ ������ � ������� �������

	if (FileEmpty(condition1)) {

		while (!feof(condition1)) {
			double ad, bd;
			fscanf(condition1, "%lf", &ad);
			fscanf(condition1, "%lf", &bd); // ��������� ���� ����� �� �����
			double ans;
			if (ad - (int)ad == 0)
			{ // �������� �������� �� ����� ������ ��� �������������
				if (bd - (int)bd == 0) { // ��� ����� �����
					if (Conditional((int)ad, (int)bd, ans)) {
						fprintf(answer1, "��� ����� %d � %d �����: %d\n",
							(int)ad, (int)bd, (int)ans);
					}
					else {
						fprintf(answer1,
							"��� ����� %d � %d �������������� ��������� ��������� �� ��������(������� �� 0)\n",
							(int)ad, (int)bd);
					}
				}
				else { // ������ ����� �����, ������ ���
					if (Conditional((int)ad, bd, ans)) {
						fprintf(answer1, "��� ����� %d � %lf �����: %lf\n",
							(int)ad, bd, ans);
					}
					else {
						fprintf(answer1,
							"��� ����� %d � %lf �������������� ��������� ��������� �� ��������(������� �� 0)\n",
							(int)ad, bd);
					}
				}
			}
			else {
				if (bd - (int)bd == 0) { // ������ ������ �����, � ������ ���
					if (Conditional(ad, (int)bd, ans)) {
						fprintf(answer1, "��� ����� %lf � %d �����: %lf\n", ad,
							(int)bd, ans);
					}
					else {
						fprintf(answer1,
							"��� ����� %lf � %d �������������� ��������� ��������� �� ��������(������� �� 0)\n",
							ad, (int)bd);
					}
				}
				else { // ��� ����� �� �����
					if (Conditional(ad, bd, ans)) {
						fprintf(answer1, "��� ����� %lf � %lf �����: %lf\n", ad,
							bd, ans);
					}
					else {
						fprintf(answer1,
							"��� ����� %lf � %lf �������������� ��������� ��������� �� ��������(������� �� 0)\n",
							ad, bd);
					}
				}
			}

		}
	}
	else {
		fprintf(answer1,
		"���� � �������� ����, ������� �� ������ ��� ���������");
	}
	fclose(condition1);
	fclose(answer1);

	/* -----------------------������ � ��������--------------------- */

	condition2a = fopen("condition2a.txt", "r");
	// ��������� ���� ��� ������ ������� ��� ������� �������
	condition2b = fopen("condition2b.txt", "r");
	answer2 = fopen("answer2.txt", "w");
	if (FileEmpty(condition2a) && FileEmpty(condition2b)) {

		// ��������� ���� ��� ������ ������ �� ������� �������
		int size = 0; // ������ � ���������� ������ �������
		double *massive = NULL; // ��������� �� ������ ������� �������
		double numbers; // ������� ��������� �����
		while (!feof(condition2a)) {
			fscanf(condition2a, "%lf", &numbers);
			size++;
			massive = (double*)realloc(massive, size*sizeof(double));
			// �������� ������ ��� ����� ������� �������
			massive[size - 1] = numbers;
			// ���������� � ������ ���� ����� �������
		}

		fprintf(answer2, "�������� �������\n");
		for (int i = 0; i < size; i++) { // ������� ������ � ���� ������
			fprintf(answer2, "%lf ", massive[i]);
		}
		fprintf(answer2, "\n");
		double b, d;
		fscanf(condition2b, "%lf", &b); // ��������� �������� �� ����� �������
		fscanf(condition2b, "%lf", &d); // ������� b<=arr[i]<=d
		fprintf(answer2,
			"���������� ������������� ��������� ������� ��� ������� %lf<=a[i]<=%lf\n",
			b, d);
		fprintf(answer2, "%d", JobToMassive(massive, size, b, d));
		// ������� � ���� ������ ����� ������������� ��������� ������� ��� ������ ���������
		free(massive);
	}
	else {
		fprintf(answer2,
			"���� �� ������ � �������� ����, ������� �� ������ ��� ���������");
	}
	printf("������� ���������, �������� ���������  ������ � ������ �� ���. �����.\n"
		);
	char o;
	printf("������� ����� ������ � ������� Enter\n");
	scanf("%c", &o);
	fclose(condition2a);
	fclose(condition2b);
	fclose(answer2);
	return 0;
}
