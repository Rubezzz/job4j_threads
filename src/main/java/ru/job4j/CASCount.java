package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int actual;
        int newCount;
        do {
            actual = count.get();
            newCount = actual + 1;
        } while (!count.compareAndSet(actual, newCount));
    }

    public int get() {
        return count.get();
    }
}