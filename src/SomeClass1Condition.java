package basics;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//These are two different ways to use inter-thread communication to achieve the same goal SomeClass1 is using
// the instance of the class as the lock as well as the condition variable. SomeClass2
// is using an explicit ReentrantLock as the lock and the Condition object as the condition
// variable. Using a ReentrantLock and the Condition allows more flexibility as the Condition class has methods like
// awaitUninterruptibly() and awaitUntil Date  deadline)which the class Object does not have,However it is more verbose.


class SomeClass1Condition {
    boolean isCompleted = false;

    public synchronized void declareSuccess() throws InterruptedException {
        while (!isCompleted) {
            wait();
        }

        System.out.println("Success!!");
    }

    public synchronized void finishWork() {
        isCompleted = true;
        notify();
    }
}
class SomeClass2 {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    boolean isCompleted = false;

    public void declareSuccess() throws InterruptedException {
        lock.lock();
        try {
            while (!isCompleted) {
                condition.await();
            }
        }
        finally {
            lock.unlock();
        }

        System.out.println("Success!!");
    }

    public void finishWork() {
        lock.lock();
        try {
            isCompleted = true;
            condition.signal();
        }
        finally {
            lock.unlock();
        }
    }
}