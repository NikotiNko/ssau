cmake_minimum_required(VERSION 3.6)
project(Task6)

set(SOURCE_LIB libTask1.a)
set(SOURCE_FILES main.cpp)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

include_directories("../Task1")
add_library(map SHARED ${SOURCE_LIB})	# Создание статической библиотеки с именем foo

add_executable(Task6 ${SOURCE_FILES})
target_link_libraries(Task6 map)		# Линковка программы с библиотекой



