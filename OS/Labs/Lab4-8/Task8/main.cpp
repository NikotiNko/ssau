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

    Sleep(1000);
    Map* importedMap = Map::input("bufferMap");
    cout << "Size:" <<importedMap->getSize()<<endl;
    cout << "Get:" <<*(importedMap->get(-122))<<endl;
    delete importedMap;
    return 0;
}