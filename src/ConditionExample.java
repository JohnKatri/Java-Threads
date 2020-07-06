package basics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    public static void main(String[] args) throws InterruptedException {
        TestClass test = new TestClass();
        Thread thread1 = new Thread(() -> {
            while (true) {
                try {
                    test.func1();
                } catch (InterruptedException e) {
                    System.out.println("Thread 1 was interrupted");
                    return;
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            while (true) {
                try {
                    test.func2();
                } catch (InterruptedException e) {
                    System.out.println("Thread 2 was interrupted");
                    return;
                }
            }
        });

        thread1.start();
        thread2.start();
        System.out.println("Sleeping");
        Thread.sleep(2000);
        System.out.println("Calling thread1 interrupt");
        thread1.interrupt();
        System.out.println("Calling thread2 interrupt");
        thread2.interrupt();

        thread1.join();
        thread2.join();
        test.printQueue();
    }

    public static class TestClass {
        private Lock lock = new ReentrantLock();
        private Condition conditionVar = lock.newCondition();
        private Queue<String> queue = new LinkedList<>();
        private volatile boolean condition = false;

        public void printQueue() {
            while (queue.peek() != null) {
                System.out.println(queue.poll());
            }
        }

        public void func1() throws InterruptedException {
            lock.lock();
            while (!condition) {
                conditionVar.await();
            }
            queue.add("func1");
            condition = false;
            lock.unlock();
        }

        public void func2() throws InterruptedException {
            lock.lockInterruptibly();
            queue.add("func2");
            condition = true;
            conditionVar.signal();
            lock.unlock();
        }
    }

}