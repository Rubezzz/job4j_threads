package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenAdd3Item() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10000);
        List<Integer> result = new ArrayList<>();
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    result.add(queue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer.start();
        consumer.join();
        producer.start();
        producer.join();
        assertThat(result).isEqualTo(List.of(1, 2, 3));
    }
}