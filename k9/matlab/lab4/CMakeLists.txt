cmake_minimum_required(VERSION 3.17)
project(lab4)

set(CMAKE_CXX_STANDARD 14)

add_executable(lab4 main.cpp Newton.h Newton.cpp)
target_link_libraries(lab4 m)
