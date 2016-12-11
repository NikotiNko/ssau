

#include <w32api/fileapi.h>
#include "map/Map.h"

int main() {

    HANDLE hFileBuffer = CreateFileA("buffer", GENERIC_READ, 0, NULL, OPEN_EXISTING, NULL, NULL);
    if (hFileBuffer != INVALID_HANDLE_VALUE) {
        Map *map = Map::input("bufferMap");
        int k, v;
        ReadFile(hFileBuffer, &k, sizeof(int), NULL, NULL);
        ReadFile(hFileBuffer, &v, sizeof(int), NULL, NULL);
        map->putSync(k, v);
        Map::output("bufferMap", map);
        CloseHandle(hFileBuffer);
        DeleteFile("buffer");
    }
    return 0;
}