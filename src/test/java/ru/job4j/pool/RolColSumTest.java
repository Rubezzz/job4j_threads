package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    private final int[][] matrix = {
            {1, 2, 3, 1},
            {3, 5, 9, 2},
            {2, 6, 4, 2},
            {5, 3, 4, 5}
    };
    private final RolColSum.Sums[] expected = {
            new RolColSum.Sums(7, 11),
            new RolColSum.Sums(19, 16),
            new RolColSum.Sums(14, 20),
            new RolColSum.Sums(17, 10),
    };

    @Test
    void whenSum() {
        assertThat(RolColSum.sum(matrix)).isEqualTo(expected);
    }

    @Test
    void whenAsyncSum() throws ExecutionException, InterruptedException {
        assertThat(RolColSum.asyncSum(matrix)).isEqualTo(expected);
    }
}