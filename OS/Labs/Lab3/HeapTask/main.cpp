#include <iostream>
#include <iomanip>
#include <w32api/ntdef.h>
#include <w32api/heapapi.h>

using namespace std;

float** createArray(HANDLE heap, unsigned int DIM1, unsigned int DIM2){
    float** ary;

    ary = (float **) HeapAlloc(heap, 0, DIM1 * sizeof(float *));

    for (int i = 0; i < DIM1; i++) {
        ary[i] = (float *) HeapAlloc(heap, 0, DIM2 * sizeof(float));
    }
    for (int i = 0; i < DIM1; i++) {
        for (int j = 0; j < DIM2; j++) {
            ary[i][j] = 1 + rand() % 1000;
        }
    }
    return ary;
}

void printArray(float **ary, unsigned int DIM1, unsigned int DIM2){
    for (int i = 0; i < DIM1; i++) {
        for (int j = 0; j < DIM2; j++) {
            cout << setw(10) << ary[i][j];
        }
        cout << endl;
    }
}

void doTask(float** ary, unsigned int DIM1, unsigned int DIM2){
    for (int i = 0; i < DIM1; i++) {
        float max = ary[i][0];
        for (int j = 1; j < DIM2; j++) {
            if (ary[i][j]>max) max = ary[i][j];
        }
        for (int j = 0; j < DIM2; j++) {
            ary[i][j] /= max;
        }
    }
}

void deleteArray(HANDLE heap, float **arr, unsigned int DIM1){
    for (int i = 0; i < DIM1; ++i) {
        HeapFree(heap, 0, arr[i]);
    }
    HeapFree(heap, 0, arr);
    HeapDestroy(heap);
}

int main() {
    HANDLE heap;
    heap = HeapCreate(0, 0, 0);
    unsigned int DIM1;
    unsigned int DIM2;

    cout << "Enter 2 dimensions of array"<<endl;
    cin >> DIM1 >> DIM2;

    float** arr = createArray(heap, DIM1, DIM2);
    printArray(arr, DIM1, DIM2);
    doTask(arr, DIM1,DIM2);
    cout << endl;
    printArray(arr, DIM1, DIM2);
    deleteArray(heap, arr, DIM1);
    return 0;
}
