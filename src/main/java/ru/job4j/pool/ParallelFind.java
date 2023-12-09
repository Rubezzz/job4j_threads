package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

public class ParallelFind extends RecursiveTask<Integer> {

    private final Object[] array;
    private final int from;
    private final int to;
    private final Object element;

    public ParallelFind(Object[] array, int from, int to, Object element) {
        if (!array[0].getClass().equals(element.getClass())) {
            throw new IllegalArgumentException("Invalid data type \"element\"");
        }
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            int index = -1;
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(element)) {
                    index = i;
                    break;
                }
            }
            return index;
        }
        int mid = (from + to) / 2;
        RecursiveTask<Integer> left = new ParallelFind(array, from, mid, element);
        RecursiveTask<Integer> right = new ParallelFind(array, from, mid, element);
        left.fork();
        right.fork();
        int leftResult = left.join();
        int rightResult = right.join();
        if (leftResult != -1) {
            return leftResult;
        }
        return rightResult;
    }
}
