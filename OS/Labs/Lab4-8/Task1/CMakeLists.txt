cmake_minimum_required(VERSION 3.6)
project(Task1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")
set(CMAKE_BUILD_TYPE Release)
set(SOURCE_FILES map/tree/Node.cpp map/tree/Node.h map/Map.cpp map/Map.h)
# Включение экспорта всех символов для создания динамической библиотеки
set ( CMAKE_WINDOWS_EXPORT_ALL_SYMBOLS ON )


add_library(mylib SHARED ${SOURCE_FILES})