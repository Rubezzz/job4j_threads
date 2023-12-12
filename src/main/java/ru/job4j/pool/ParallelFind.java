package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFind<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public ParallelFind(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    private int findIndex() {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(element)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return findIndex();
        }
        int mid = (from + to) / 2;
        ParallelFind<T> left = new ParallelFind<>(array, from, mid, element);
        ParallelFind<T> right = new ParallelFind<>(array, mid, to, element);
        left.fork();
        right.fork();
        return Math.max(left.join(), right.join());
    }

    public static <E> int find(E[] array, E element) {
        ParallelFind<E> find = new ParallelFind<>(array, 0, array.length - 1, element);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(find);
    }
}
