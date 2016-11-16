#ifndef TASK1_MAP_H
#define TASK1_MAP_H


#include "tree/Node.h"

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
};


#endif //TASK1_MAP_H
