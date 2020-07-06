package basics;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AuthenticationThreadJavaWithCondition {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static String username;
    private static String password;
    private static boolean isLoged = false;

    public static void main(String[] args) {
        Authentication authentication = new Authentication();
        UserInt userInt = new UserInt();
        authentication.start();
        userInt.start();
    }

    public static class Authentication extends Thread {

        @Override
        public void run() {
            lock.lock();
            try {
                while (username == null || password == null || (username.length() == 0 || password.length() == 0)) {
                    condition.await();
                }
                isLoged = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("Accessing database to authenticate credentials");
        }
    }

    public static class UserInt extends Thread {

        @Override
        public void run() {

            while (!isLoged) {
                lock.lock();
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("-------------------------------");
                    System.out.println("Welcome to Login form");
                    System.out.print("Username:  ");
                    username = scanner.nextLine();
                    System.out.println();
                    System.out.print("Password: ");
                    password = scanner.nextLine();
                    condition.signal();
                } finally {
                    lock.unlock();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("You are logged in.");
        }
    }
}