cmake_minimum_required(VERSION 3.6)
project(Task7)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp map/Map.cpp map/Map.h map/tree/Node.cpp map/tree/Node.h)
add_executable(Task7 ${SOURCE_FILES})