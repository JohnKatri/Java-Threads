package basics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HackerVaultGame {

    public static int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        threads.forEach(Thread::start);
    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
            System.out.println(password);
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }

            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread{
        protected Vault vault;

        public HackerThread (Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            System.out.println(this.getName()  + "Starting Cracking The Password");
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guess the password");
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread (Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            System.out.println(this.getName()  + "Starting Cracking The Password");
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if(this.vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guess the password");
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {

        @Override
        public void run() {
            for (int seconds = 10; seconds >= 0; seconds--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                System.out.println(seconds);
            }
            System.out.println("Gave Over Police Arrived!!!");
            System.exit(0);
        }
    }
}
