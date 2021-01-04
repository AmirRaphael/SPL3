all: BGRSclient

BGRSclient: bin/BGRSclient.o bin/BGRSconnectionHandler.o bin/LockingQueue.o
	g++ -o bin/BGRSclient bin/BGRSclient.o bin/BGRSconnectionHandler.o bin/LockingQueue.o -lboost_system -pthread

bin/BGRSclient.o: src/BGRSclient.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/BGRSclient.o src/BGRSclient.cpp

bin/BGRSconnectionHandler.o: src/BGRSconnectionHandler.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/BGRSconnectionHandler.o src/BGRSconnectionHandler.cpp
	
bin/LockingQueue.o: src/LockingQueue.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/LockingQueue.o src/LockingQueue.cpp

clean: 
	rm -f bin/*