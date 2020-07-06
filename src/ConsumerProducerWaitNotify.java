package basics;

import java.util.Random;

public class ConsumerProducerWaitNotify {

    public static void main(String[] args) {
        Message2 message = new Message2();
        (new Thread(new Writer2(message))).start();
        (new Thread(new Reader2(message))).start();
    }
}

class Message2 {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while(empty) {
            try {
                wait();
            } catch(InterruptedException e) {

            }

        }
        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void write(String message) {
        while(!empty) {
            try {
                wait();
            } catch(InterruptedException e) {

            }

        }
        empty = false;
        this.message = message;
        notifyAll();
    }
}

class Writer2 implements Runnable {
    private Message2 message;

    public Writer2(Message2 message) {
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

class Reader2 implements Runnable {
    private Message2 message;

    public Reader2(Message2 message) {
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















