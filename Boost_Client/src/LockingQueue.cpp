#include "LockingQueue.h"

template<typename T>
void LockingQueue<T>::push(const T &_data) {
    {
        std::lock_guard<std::mutex> lock(guard);
        queue.push(_data);
    }
    signal.notify_one();
}

template<typename T>
bool LockingQueue<T>::empty() const {
    std::lock_guard<std::mutex> lock(guard);
    return queue.empty();
}

template<typename T>
bool LockingQueue<T>::tryPop(T &_value) {
    std::lock_guard<std::mutex> lock(guard);
    if (queue.empty()) {
        return false;
    }

    _value = queue.front();
    queue.pop();
    return true;
}

template<typename T>
void LockingQueue<T>::waitAndPop(T &_value) {
    std::unique_lock<std::mutex> lock(guard);
    while (queue.empty()) {
        signal.wait(lock);
    }

    _value = queue.front();
    queue.pop();
}
