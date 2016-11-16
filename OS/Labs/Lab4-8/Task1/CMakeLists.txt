cmake_minimum_required(VERSION 3.6)
project(Task1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp map/tree/Node.cpp map/tree/Node.h map/Map.cpp map/Map.h)
add_executable(Task1 ${SOURCE_FILES})