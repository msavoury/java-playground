package com.codinghabit.playground;


import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    public static void main(String [] args) {
        Semaphore s = new Semaphore(4);
        int numWorkers = 10;
        for (int i = 0; i < numWorkers; i++) {
            new Thread(getWorker(i, s)).start();
        }
    }

    public static Runnable getWorker(final int id, final Semaphore semaphore) {
        return new Runnable() {
          public void run() {
              Random r = new Random();
              int loops = r.nextInt(6) + 1;
              int sleepSeconds = r.nextInt(2) + 1;
              System.out.println("Thread " + id + " will loop " + loops + " times");
              for (int i = 0; i < loops; i++){
                  try {
                      System.out.println("Thread " + id + " attempting to acquire ticket");
                      semaphore.acquire();
                      System.out.println("Thread " + id + " acquired ticket");
                      System.out.println("Thread " + id + " sleeping for " + sleepSeconds + " seconds");
                      Thread.sleep(sleepSeconds * 1000);
                      semaphore.release();
                      System.out.println("Thread " + id + " released ticket");
                  }
                  catch(InterruptedException ie){
                      System.out.println("Interrupted");
                  }
              }
          }

        };
    }
}
