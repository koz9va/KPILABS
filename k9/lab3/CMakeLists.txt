cmake_minimum_required(VERSION 3.17)
project(lab3)

set(CMAKE_CXX_STANDARD 14)

find_package(Boost REQUIRED)

include_directories(${Boost_INCLUDE_DIRS})
add_executable(lab3 main.cpp CallAndWrite.cpp CallAndWrite.h NU.h NU.cpp)

target_link_libraries(lab3 ${Boost_LIBRARIES})