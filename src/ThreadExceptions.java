package basics;

public class ThreadExceptions {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Intentional Exception");
            }
        });

        thread.setName(" - Misbehaving NewThread- ");

//        This handler will caught an exception that was thrown inside the thread and did not get caught anywhere.
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("critical error happened to thread " + t.getName());
                System.out.println("This error is " + e.getMessage());
            }
        });

        thread.start();

    }
}
