package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void increment() throws InterruptedException {
        CASCount cas = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 500_000; i++) {
                cas.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 500_000; i++) {
                cas.increment();
            }
        });
        int expected = 1_000_000;
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cas.get()).isEqualTo(expected);
    }
}