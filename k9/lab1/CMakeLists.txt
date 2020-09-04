cmake_minimum_required(VERSION 3.17)
project(lab1)

set(CMAKE_CXX_STANDARD 14)

find_package(Boost COMPONENTS program_options REQUIRED )

include_directories(${BOOST_INCLUDE_DIRS})
add_executable(lab1 main.cpp)
target_link_libraries(lab1 LINK_PUBLIC ${Boost_LIBRARIES})