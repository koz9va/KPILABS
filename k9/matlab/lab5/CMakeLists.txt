cmake_minimum_required(VERSION 3.19)
project(lab5)

set(CMAKE_CXX_STANDARD 14)


add_executable(lab5
        main.cpp
        ode.cpp
        ode.h
        include/SLAU.h
        include/SLAU.cpp)
