#pragma once

#include "tree/Node.h"
#include <w32api/handleapi.h>
#include <w32api/synchapi.h>

class Map {

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
    Node* buffer;
    HANDLE hMutex;

    DWORD putAsync(CONST LPVOID lpParam);
};