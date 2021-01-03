#ifndef BOOST_CLIENT_LOCKINGQUEUE_H
#define BOOST_CLIENT_LOCKINGQUEUE_H

#include <queue>
#include <mutex>
#include <condition_variable>

template<typename T>
class LockingQueue {

private:
    std::queue<T> queue;
    mutable std::mutex guard;
    std::condition_variable signal;

public:
    void push(T const &_data);

    bool empty() const;

    bool tryPop(T &_value);

    void waitAndPop(T &_value);
};

#endif //BOOST_CLIENT_LOCKINGQUEUE_H