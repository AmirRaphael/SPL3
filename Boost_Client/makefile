CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system

all: BGRSclient
	g++ -o bin/BGRSclient bin/BGRSconnectionHandler.o bin/BGRSclient.o bin/LockingQueue.o $(LDFLAGS) 

BGRSclient: bin/BGRSconnectionHandler.o bin/BGRSclient.o
	
bin/BGRSconnectionHandler.o: src/BGRSconnectionHandler.cpp
	g++ $(CFLAGS) -o bin/BGRSconnectionHandler.o src/BGRSconnectionHandler.cpp

bin/BGRSclient.o: src/BGRSclient.cpp
	g++ $(CFLAGS) -o bin/BGRSclient.o src/BGRSclient.cpp
	
bin/LockingQueue.o: src/LockingQueue.cpp
	g++ $(CFLAGS) -o bin/LockingQueue.o src/LockingQueue.cpp
	
.PHONY: clean
clean:
	rm -f bin/*