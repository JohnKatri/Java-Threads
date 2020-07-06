package basics;


import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreConsumerProducer {

    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
    }
}

class Message {
    private String message;
    private Semaphore reader = new Semaphore(0);
    private Semaphore writer = new Semaphore(1);

    public String read() {

        try{
            reader.acquire();
        } catch (InterruptedException e) {

        }
        String tempMessage = message;
        writer.release();
        return tempMessage;
    }

    public void write(String message) {

        try{
            writer.acquire();
        } catch (InterruptedException e) {

        }
        this.message = message;
        reader.release();
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String messages[] = {
                "Humpty Dumpty sat on a wall",
                "Humpty Dumpty had a great fall",
                "All the king's horses and all the king's men",
                "Couldn't put Humpty together again"
        };

        Random random = new Random();

        for(int i=0; i<messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch(InterruptedException e) {

            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        Random random = new Random();
        for(String latestMessage = message.read(); !latestMessage.equals("Finished");
            latestMessage = message.read()) {
            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch(InterruptedException e) {

            }
        }
    }
}















