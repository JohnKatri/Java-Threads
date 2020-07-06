package basics;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockSolvedWithLock {

    //    The example of trains is just to demonstrate what a dead lock is
//    and how we can get into it if we are not careful when we are using more than 1 lock.
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));

        trainAThread.start();
        trainBThread.start();
    }

    public static class TrainB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                }

                intersection.takeRoadB();
            }
        }
    }

    public static class TrainA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                }
                intersection.takeRoadA();
            }
        }
    }

    public static class Intersection {
        private Lock roadA = new ReentrantLock(true);
        private Lock roadB = new ReentrantLock(true);

        public void takeRoadA() {
            roadA.lock();
            System.out.println("Road A is locked by thread " + Thread.currentThread().getName());

            roadB.lock();
            System.out.println("Train is passing through road A");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            roadB.unlock();
            roadA.unlock();
        }

        public void takeRoadB() {
            roadA.lock();
            System.out.println("Road B is locked by thread " + Thread.currentThread().getName());

            roadB.lock();
            System.out.println("Train is passing through road B");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            roadB.unlock();
            roadA.unlock();
        }
    }
}
