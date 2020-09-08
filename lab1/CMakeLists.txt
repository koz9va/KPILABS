cmake_minimum_required(VERSION 3.17)
project(lab1)

set(CMAKE_CXX_STANDARD 17)

find_package(Boost)
if(Boost_FOUND)
    include_directories(${Boost_INCLUDE_DIRS})
    add_executable(lab1 main.cpp)
    target_link_libraries(lab1 ${Boost_LIBRARIES})

endif()
