cmake_minimum_required(VERSION 3.6)
project(Task8)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES1 main.cpp map/Map.cpp map/Map.h map/tree/Node.cpp map/tree/Node.h)
set(SOURCE_FILES2 modifyer.cpp map/Map.cpp map/Map.h map/tree/Node.cpp map/tree/Node.h)

add_executable(Task8 ${SOURCE_FILES1})
add_executable(Modifyer ${SOURCE_FILES2})