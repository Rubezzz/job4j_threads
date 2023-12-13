package ru.job4j.pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    private static Sums calcSum(int[][] matrix, int i) {
        int rowSum = Arrays.stream(matrix[i]).sum();
        int colSum = 0;
        for (int[] ints : matrix) {
            colSum += ints[i];
        }
        return new Sums(rowSum, colSum);
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] result = new Sums[size];
        for (int i = 0; i < size; i++) {
            result[i] = calcSum(matrix, i);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            int finalI = i;
            futures.put(i, CompletableFuture.supplyAsync(() -> calcSum(matrix, finalI)));
        }
        Sums[] result = new Sums[size];
        for (Integer key : futures.keySet()) {
            result[key] = futures.get(key).get();
        }
        return result;
    }
}