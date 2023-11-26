package ru.job4j.concurrent.coccurent;

public class Wget {
    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            System.out.print("\rLoading : " + i  + "%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
