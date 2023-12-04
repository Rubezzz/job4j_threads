package ru.job4j;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public int getCount() {
        synchronized (monitor) {
            return count;
        }
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count >= total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(10);
        Thread first = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                countBarrier.await();
                countBarrier.count();
                System.out.printf("First thread work. Count = %s%n", countBarrier.getCount());
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                countBarrier.await();
                countBarrier.count();
                System.out.printf("Second thread work. Count = %s%n", countBarrier.getCount());
            }
        });
        first.start();
        second.start();
    }
}