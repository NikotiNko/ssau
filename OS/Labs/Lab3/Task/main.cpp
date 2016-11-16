#include <iostream>
#include <iomanip>

using namespace std;

const unsigned int DIM1 = 4;
const unsigned int DIM2 = 5;

float ary[DIM1][DIM2];

void createAry(){
    for (int i = 0; i < DIM1; i++) {
        for (int j = 0; j < DIM2; j++) {
            ary[i][j] =  1 + rand() % 1000;
        }
    }
}

void printAry(){
    for (int i = 0; i < DIM1; i++) {
        for (int j = 0; j < DIM2; j++) {
            cout << setw(10) << ary[i][j];
        }
        cout << endl;
    }
}

void doTaskWithAry(){
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

int main() {
    createAry();
    printAry();
    doTaskWithAry();
    cout << endl;
    printAry();

    delete [] ary;
    return 0;
}
