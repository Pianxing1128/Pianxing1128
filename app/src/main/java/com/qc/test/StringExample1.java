package com.qc.test;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.*;

@Slf4j
@NotThreadSafe
public class StringExample1 {

    // 请求总数
    public static int clientTotal = 10;

    // 同时并发执行的线程数
    public static int threadTotal = 2;

    public static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {

                    update();

                    cyclicBarrier.await();

                } catch (Exception e) {
                    log.error("exception:", e);
                }
            }).start();
        }
        executorService.shutdown();
        log.info(("size:{}"), stringBuilder.length());

    }

    private static void update() {
        stringBuilder.append("1");
    }
}