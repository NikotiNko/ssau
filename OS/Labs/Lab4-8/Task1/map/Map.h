#pragma once
#ifdef LABDLL_EXPORTS
#define LABDLL_API __declspec(dllexport)
#else
#define LABDLL_API __declspec(dllimport)
#endif

#include "tree/Node.h"

class __declspec(dllexport) Map {

public:
    int* get(int key);

    void put(int key, int val);

    void remove(int key);

    int getSize();

    static Map* input(char* filename);

    static void output(char* filename, Map* source);

    Map();

    ~Map();

private:
    Node* rootNode;
    int* size;
};