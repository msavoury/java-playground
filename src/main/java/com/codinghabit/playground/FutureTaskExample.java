package com.codinghabit.playground;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.Random;

/*
 This example class illustrates the usage of a FutureTask, which can be used
 as an abstraction for computations.
 */
public class FutureTaskExample {

    public static void main(String [] args) {
        //doInMainThread();
        //doInSeparateThread();
        useCallable();
    }

    /*
    A FutureTask CAN run in a separate thread but doesn't do so by default. In this method,
    we create/run the future tasks in the main thread. Because of this, the thread blocks as the
    'run()' methods complete.  The debug println will not execute until 'run' returns
    */
    public static void doInMainThread() {
        Callable<Integer> callable1 = generateCallable();
        Callable<Integer> callable2 = generateCallable();

        FutureTask<Integer> task1 = new FutureTask<Integer>(callable1);
        FutureTask<Integer> task2 = new FutureTask<Integer>(callable2);
        task1.run();
        task2.run();

        System.out.println("Called run method"); //This isn't printed until the calls to run() have completed
        try {
            int result = task1.get();
            int result2 = task2.get();
            System.out.println("Result1 is " + result);
            System.out.println("Result2 is " + result2);
        }
        catch(ExecutionException e) {
            System.out.println(e.getMessage() );
            e.printStackTrace();
        }
        catch(InterruptedException ie) {
            System.out.println(ie.getMessage() + ie.getCause());
        }
    }

    /*
     In this example, we take advantage of the fact that FutureTask implements Runnable. We can wrap these
     tasks in Threads so that they can be run in a separate thread.  In this example, the println executes
     immediately, without waiting for the 'run' methods to complete.
     */
    public static void doInSeparateThread() {
        Callable<Integer> callable1 = generateCallable();
        Callable<Integer> callable2 = generateCallable();

        FutureTask<Integer> task1 = new FutureTask<Integer>(callable1);
        FutureTask<Integer> task2 = new FutureTask<Integer>(callable2);
        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();
        System.out.println("Called run method"); //This is printed immediately
        try {
            int result = task1.get();
            int result2 = task2.get();
            System.out.println("Result1 is " + result);
            System.out.println("Result2 is " + result2);
        }
        catch(ExecutionException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch(InterruptedException ie) {
            System.out.println(ie.getMessage() + ie.getCause());
        }
    }

    public static Callable<Integer> generateCallable() {
        return  new Callable<Integer>() {
            public Integer call() {
                if (System.currentTimeMillis() % 3 == 0) throw new UnsupportedOperationException();
                Random random = new Random();
                int result = 0;
                for (int i = 0; i < 1000000000; i++) {
                    result = random.nextInt();
                }
                return result;
            }
        };
    }

    public static void useCallable() {
        Callable<Integer> c = generateCallable();
        try {
            Integer myValue = c.call();
            System.out.println("The value from the callable is " + myValue);
        }
        catch(Exception e) {

        }
    }
}
