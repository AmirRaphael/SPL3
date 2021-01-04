#include <stdlib.h>
#include <BGRSconnectionHandler.h>
#include <LockingQueue.h>
#include <thread>
#include <queue>
#include <mutex>
#include <condition_variable>

#define BUFSIZE 1024

class KeyboardListener {
private:
    bool& terminate;
    LockingQueue<std::string>* msgQueue;
    std::condition_variable& cv;
    std::mutex& logoutLock;
    char buf[BUFSIZE];
public:
    KeyboardListener(bool& terminate, LockingQueue<std::string>* msgQueue, 
					std::condition_variable& cv, std::mutex& logoutLock) 
					: terminate(terminate), msgQueue(msgQueue), cv(cv), logoutLock(logoutLock) {}
    void run(){
        while (!terminate){
            std::cin.getline(buf, BUFSIZE);
            std::string msg(buf);
            msgQueue -> push(msg);
            if (msg == "LOGOUT"){
                std::unique_lock<std::mutex> lk(logoutLock);
                cv.wait(lk);
				lk.unlock();
            }
        }
    }
};

int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
	
    std::string host = argv[1];
    short port = atoi(argv[2]);

    BGRSconnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
	
    bool terminate = false;
	LockingQueue<std::string> msgQueue;
    std::mutex logoutLock;
    std::condition_variable cv;
    KeyboardListener listener(terminate, msgQueue, cv, logoutLock);
	
	//Start Keyboard listener thread
    std::thread listenerThread (&KeyboardListener::run, &listener);

    while (!terminate) {
        std::string line;
        msgQueue -> waitAndPop(line);
		
		//Send message to server
        if (!connectionHandler.sendMessage(line)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
		
        std::string part1;
        std::string part2;
		//Get answer form server
        if (!connectionHandler.getMessage(part1,part2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }

		//Print answer to client console
        std::cout << part1 << std::endl;
		if(part2.length() > 0) {std::cout << part2 << std::endl;}
		
		//Check for termination condition due to logout
		if(part1 == "ACK 4" || part1 == "ERR 4") {
			{//Local scope for lock_guard
			std::lock_guard<std::mutex> lk(logoutLock);
			terminate = (part1 == "ACK 4");
			}
			//Notify keyboard listener thread
			cv.notify_all();
		}
    }
	
    listenerThread.join();
    return 0;
}