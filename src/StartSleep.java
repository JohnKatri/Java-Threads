package basics;

public class StartSleep {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are now in thread" + Thread.currentThread().getName());
                System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
            }
        });


        thread.setName(" -New Worker NewThread- ");

//        between 1 and 10
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread  " + Thread.currentThread().getName() + " before we start the thread");
        thread.start();
        System.out.println("We are in thread " + Thread.currentThread().getName() + " After we start the thread");

//      instructs the operating system do not schedule until the time passes. During this time thread is not consuming and CPU.
        Thread.sleep(1000);
    }
}
