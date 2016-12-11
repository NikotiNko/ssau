#include <iostream>
#include "map/Map.h"

using namespace std;

int main() {
    Map* map = new Map();
    map->put(0,100);
    map->put(-10,-150);
    map->put(10,150);
    map->put(12,130);
    map->put(14,111);
    map->put(11,222);
    map->put(122,84);
    map->put(-122,-875);
    map->put(54,9212);

    cout << "Size:" <<map->getSize()<<endl;
    cout << "Get:" <<map->get(-10)<<endl;

    return 0;
}