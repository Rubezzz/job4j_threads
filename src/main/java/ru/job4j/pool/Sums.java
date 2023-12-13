package ru.job4j.pool;

import java.util.Objects;

public class Sums {
    private int rowSum;
    private int colSum;

    public Sums(int rowSum, int colSum) {
        this.rowSum = rowSum;
        this.colSum = colSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sums sums = (Sums) o;
        return rowSum == sums.rowSum && colSum == sums.colSum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowSum, colSum);
    }

    @Override
    public String toString() {
        return "Sums{"
                + "rowSum=" + rowSum
                + ", colSum=" + colSum
                + '}';
    }
}
