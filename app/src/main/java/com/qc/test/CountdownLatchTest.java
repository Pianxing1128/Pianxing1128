package com.qc.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountdownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);
        Service service = new Service(latch);
        Runnable task = () -> service.run();
        for (int i = 1; i <= 3; i++) {
            Thread thread = new Thread(task);
            thread.start();
        }
        System.out.println("主线程等待... ");
        latch.await();
        System.out.println("主线程完成等待... ");
    }
}

class Service {
    private CountDownLatch latch;
    public Service(CountDownLatch latch) {
        this.latch = latch;
    }
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " 执行... ");
            sleep(3);
            System.out.println(Thread.currentThread().getName() + " 完成执行... ");
        } finally {
            latch.countDown();
        }
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
