cmake_minimum_required(VERSION 3.17)
project(Boost_Client)
find_package(Boost 1.75.0 COMPONENTS system filesystem REQUIRED)
set(CMAKE_CXX_STANDARD 14)

include_directories(include
        ${Boost_INCLUDE_DIR})

add_executable(Boost_Client
        include/BGRSconnectionHandler.h
        src/BGRSclient.cpp
        src/BGRSconnectionHandler.cpp)
