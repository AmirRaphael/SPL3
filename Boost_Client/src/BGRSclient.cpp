#define BUFSIZE 1024
#include <stdlib.h>
#include <connectionHandler.h>
#include <BGRSconnectionHandler.h>
#include <thread>
#include <queue>
#include <LockingQueue.h>
#include <mutex>
#include <condition_variable>
class KeyboardListener {
private:
    bool& shouldTerminate;
    std::condition_variable& cv;
    std::mutex& logoutLock;
    char buf[BUFSIZE];
    LockingQueue<std::string>* msgQueue;
public:
    KeyboardListener(LockingQueue<std::string>* msgQueue,std::mutex& logoutLock,bool& term,std::condition_variable& cv): buf(), msgQueue(msgQueue),
                        logoutLock(logoutLock), cv(cv), shouldTerminate(term){}

    void run(){
        while (!shouldTerminate){
            std::cin.getline(buf, BUFSIZE);
            std::string msg(buf);
            msgQueue->push(msg);
            if (msg=="LOGOUT"){
                std::unique_lock<std::mutex> lock(logoutLock);
                cv.wait(lock);
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
    LockingQueue<std::string>* msgQueue = new LockingQueue<std::string>();
    bool term = false;
    std::mutex msgLock;
    std::condition_variable cv;
    KeyboardListener listener(msgQueue,msgLock,term,cv);
    std::thread listenerThread (&KeyboardListener::run,&listener);

    while (1) {
        std::string line;
        msgQueue->waitAndPop(line);
        int len=line.length();
        if (!connectionHandler.sendMessage(line)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }

//        std::cout << "Sent " << len+1 << " bytes to server" << std::endl;


        std::string part1;
        std::string part2;

        if (!connectionHandler.getMessage(part1,part2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }

        std::cout<<part1<<std::endl;
        if (!part2.empty()){
            std::cout<<part2<<std::endl;
        }

        if (part1=="ACK 4"||part1=="ERR 4"){
            if (part1=="ACK 4"){
                std::lock_guard<std::mutex> lk(msgLock);
                term=true;
                cv.notify_all();
                break;
            }
            cv.notify_all();
        }


    }
    listenerThread.join();
    delete msgQueue;
    return 0;
}
