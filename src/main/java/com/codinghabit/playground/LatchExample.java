package com.codinghabit.playground;

import java.util.concurrent.CountDownLatch;

/**
 * Created by msavoury on 1/30/17.
 */
public class LatchExample {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String [] args) {
       //simpleLatch();
        sequentialLatch();
    }

    /*
    * This example shows how once a latch reaches it's terminal state, all other threads calling await on that
    * latch will not block and proceed immediately
    */
    public static void simpleLatch() {
        try {
            Runnable r1 = getRunner(latch, null);
            Runnable r2 = getRunner(latch, null);
            new Thread(r1).start(); //This thread will have to wait
            System.out.println("Going to sleep!");
            Thread.sleep(5000);
            System.out.println("Done sleeping");
            latch.countDown();

            //By the time this thread starts, the latch has already been opened so it
            //proceeds without ever blocking
            new Thread(r2).start();

        }
        catch(InterruptedException ie) {
            System.out.println("I was interrupted!");
        }

    }

    /**
     * This example shows how you can set up a system where one thread waits on another
     */
    public static void sequentialLatch() {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        Runnable r1 = getRunner(latch1, latch2);
        Runnable r2 = getRunner(latch2, null);
        new Thread(r1).start();
        new Thread(r2).start();

        try {
            System.out.println("Going to sleep!");
            Thread.sleep(5000);
            System.out.println("Done sleeping");
            latch1.countDown();
        }
        catch (InterruptedException ie) {
            System.out.println("I was interrupted!");

        }
    }

    public static Runnable getRunner(final CountDownLatch l, final CountDownLatch l2) {
        return new Runnable() {
            public void run() {
                try {
                    System.out.println("Thread " + this.toString() + " waiting on latch " +l.toString() +" to get through!");
                    l.await();
                    System.out.println("Thread " + this.toString() + " got through!");
                    if (l2 != null) {
                        System.out.println("Thread " + this.toString() + " unlocking latch " + l2.toString());
                        l2.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
