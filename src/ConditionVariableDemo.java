package basics;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariableDemo {

    public static void main(String[] args){
        Lock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();
        SharedResource sharedRes = new SharedResource();

        Producer producer = new Producer(lock, condition, sharedRes);
        Consumer consumer = new Consumer(lock, condition, sharedRes);

        consumer.start();
        producer.start();

    }

    public static class SharedResource{
        String sharedVar = null;

        public String getSharedVar(){
            return this.sharedVar;
        }

        public void setSharedVar(String input){
            this.sharedVar = input;
        }
    }

    public static class Producer extends Thread{
        Lock lock;
        Condition condition;
        SharedResource sharedRes;
        public Producer(Lock lock, Condition condition, SharedResource res){
            this.lock = lock;
            this.condition = condition;
            this.sharedRes = res;
        }

        @Override
        public void run(){
            this.lock.lock();
            try{
                Thread.sleep(1000);
                this.condition.signal();
                this.lock.unlock();
                Thread.sleep(500);
                this.lock.lock();

                Thread.sleep(1000);
                this.condition.signal();
                this.lock.unlock();
                Thread.sleep(500);
                this.lock.lock();

                Thread.sleep(1000);
                this.condition.signal();
                this.lock.unlock();
                Thread.sleep(500);
                this.lock.lock();

                Thread.sleep(1000);
                this.condition.signal();
                this.lock.unlock();
                Thread.sleep(500);
                this.lock.lock();

                Thread.sleep(1000);
                this.condition.signal();
                this.lock.unlock();
                Thread.sleep(500);
                this.lock.lock();

                Thread.sleep(1000);
                this.condition.signal();
                this.lock.unlock();
                Thread.sleep(500);
                this.lock.lock();

                this.sharedRes.setSharedVar("New value");
                this.condition.signal();// awake any thread blocked on the condition's await method
            }catch(InterruptedException iex){

            }
            finally{
                this.lock.unlock();
            }
        }
    }

    public static class Consumer extends Thread{
        Lock lock;
        Condition condition;
        SharedResource sharedRes;
        public Consumer(Lock lock, Condition condition, SharedResource res){
            this.lock = lock;
            this.condition = condition;
            this.sharedRes = res;
        }

        @Override
        public void run(){
            this.lock.lock();
            try{
                while(this.sharedRes.getSharedVar() == null){
                    System.out.println("No value yet");
                    this.condition.await();// put this thread to sleep and release the lock associated to condition
                }
            }catch(InterruptedException iex){
            }
            finally{
                this.lock.unlock();
            }
            // do other stuff
            System.out.println("Value of shared resource is "+ this.sharedRes.getSharedVar());
        }
    }
}