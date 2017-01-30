package com.codinghabit.playground;

import java.util.*;
//import java.util.concurrent.atomic.

public class SynchronizedExample {
    public static void main(String [] args) {
        //Lists by default are NOT thread-safe. But, we can wrap them to make them
        //thread safe as below:
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<Integer>());
        Set<Double> syncSet = Collections.synchronizedSet(new HashSet<Double>());

    }
}
