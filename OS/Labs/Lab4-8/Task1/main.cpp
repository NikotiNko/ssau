#include <iostream>
#include "map/Map.h"


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

    //Serialization
    Map::output("test.txt",map);
    Map* importedMap = Map::input("test.txt");
    delete map;
    delete importedMap;
    return 0;
}