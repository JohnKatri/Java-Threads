package basics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaPhoreExample {

    private final Semaphore semaphore;
    private List<Integer> arrayList;

    public SemaPhoreExample(int limit) {
        this.arrayList = Collections.synchronizedList(new ArrayList<Integer>());
        this.semaphore = new Semaphore(limit);
    }

    public boolean add(Integer i) throws InterruptedException {
        boolean added = false;
        semaphore.acquire();
        added = arrayList.add(i);
        if (!added) semaphore.release();
        return added;
    }

    public boolean remove(Integer i) {
        boolean removed = arrayList.remove(i);
        if (removed)
            semaphore.release();
        return removed;
    }


    public static void main(String[] args) throws InterruptedException {
        final SemaPhoreExample semaPhoreExample = new SemaPhoreExample(10);

        Thread first  = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    if (semaPhoreExample.add(i))
                        System.out.println(Thread.currentThread().getName() + " adding value: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });




        Thread second  = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 20; i++) {
                if (semaPhoreExample.remove(i))
                    System.out.println(Thread.currentThread().getName() + " removing value: " + i);
            }

        });

        first.start();
        second.start();

        first.join(1);
        second.join(1);
        System.out.println("HELLO");
    }
}












