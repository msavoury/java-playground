package com.codinghabit.playground;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This example shows the usage of a Barrier. In this example there are 4 friends who are traveling into Canada
 * via the east coast of the USA, but each has their preferred method of travel, (walking, bus, train, plane).
 * Instead of going straight there, they decide to sync up at certain spots along the way.  Once they all arrive
 * at a location, they depart once again to the next destination and repeat.
 */
public class BarrierExample {

    //Trip up the east coast of the USA
    public static String[] locations = {"Miami", "Jacksonville", "Washington", "New York", "Albany", "Montreal"};
    public static CyclicBarrier barrier;

    public static void main(String [] args) {
        Thread walker = new Thread(getRunnable("walker", 5));
        Thread bus = new Thread(getRunnable("bus", 4));
        Thread train = new Thread(getRunnable("train", 3));
        Thread plane = new Thread(getRunnable("plane", 2));

        barrier = new CyclicBarrier(4);

        walker.start();
        bus.start();
        train.start();
        plane.start();
    }

    public static Runnable getRunnable(final String type, final int secs) {
        return new Runnable() {
            public void run() {
                System.out.println("Thread " + type + " starting trip");
                for (int i = 0; i < locations.length; i++) {
                    System.out.println("Thread " + type + " leaving for " + locations[i]);
                    try {
                        Thread.sleep(secs * 1000); //simulates time it takes to travel
                        System.out.println("Thread " + type + " reached " + locations[i]);
                        System.out.println("Thread " + type + " waiting for others...");
                        barrier.await();
                    }
                    catch(InterruptedException ie) {
                        System.out.println("Thread " + type + " interrupted");
                    }
                    catch(BrokenBarrierException bbe) {
                        System.out.println("Barrier has been broken!");
                    }
                }
                System.out.println("Thread " + type + " finished trip!");
            }
        };
    }
}
