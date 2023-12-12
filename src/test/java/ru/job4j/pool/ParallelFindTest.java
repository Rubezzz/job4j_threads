package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.*;

class ParallelFindTest {

    @Test
    void whenRecursiveFindThenOk() {
        String[] array = new String[10_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = "String - " + i;
        }
        String found = "String - 755";
        int expected = 755;
        int result = ParallelFind.find(array, found);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenLinearFindThenOk() {
        String[] array = new String[9];
        for (int i = 0; i < array.length; i++) {
            array[i] = "String - " + i;
        }
        String found = "String - 8";
        int expected = 8;
        int result = ParallelFind.find(array, found);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenElementNotFoundThenMinus1() {
        String[] array = new String[10_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = "String - " + i;
        }
        String found = "String - 99999";
        int expected = -1;
        int result = ParallelFind.find(array, found);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenElementIsOtherTypeThenMinus1() {
        Object[] array = new Object[3];
        array[0] = "string";
        array[1] = 10;
        array[2] = new LinkedList<>();
        Double found = 5d;
        int expected = -1;
        int result = ParallelFind.find(array, found);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenElementIsOtherTypeThenMinus1Version2() {
        String[] array = new String[10_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = "String - " + i;
        }
        Double found = 5d;
        int expected = -1;
        int result = ParallelFind.find(array, found);
        assertThat(result).isEqualTo(expected);
    }
}