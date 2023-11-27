package ru.job4j.concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int limit;

    public Wget(String url, int speed) {
        this.url = url;
        this.limit = speed;
    }

    private String getFileName() {
        String[] split = url.split("/");
        return split[split.length - 1];
    }

    private void sleep(long sleepTime) {
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        var file = new File(getFileName());
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[128];
            int bytesRead;
            int countBytes = 0;
            var downloadAt = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                countBytes += bytesRead;
                if (countBytes >= limit) {
                    long elapsedTime = System.currentTimeMillis() - downloadAt;
                    long sleepTime = 1000 - elapsedTime;
                    if (sleepTime > 0) {
                        sleep(sleepTime);
                    }
                    downloadAt = System.currentTimeMillis();
                    countBytes = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid number of startup arguments.");
        }
        try {
            new URL(args[0]);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL argument.");
        }
        if (!args[1].matches("\\d+")) {
            throw new IllegalArgumentException("Invalid speed argument.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
