package basics;

public class InterruptThread {

    public static void main(String[] args) {
        Thread blockingTask = new Thread(new BlockingTask());

        blockingTask.start();

        blockingTask.interrupt();
    }


    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(5000000);
            } catch (InterruptedException e) {
                System.out.println("It was Interrupted");
            }
        }
    }
}
