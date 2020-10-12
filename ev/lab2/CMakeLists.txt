cmake_minimum_required(VERSION 3.17)
project(lab2)

set(CMAKE_C_STANDARD 99)

add_executable(lab2 main.c algorithms/LU.h algorithms/QR.h algorithms/LDL.h)
target_link_libraries(lab2 m)