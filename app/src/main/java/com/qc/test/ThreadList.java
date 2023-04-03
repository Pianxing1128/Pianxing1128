package com.qc.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.*;
@Slf4j
public class ThreadList {
    @RequestMapping("/thread/test")
    public String courseThreadInfo() {

        StringBuilder ss = new StringBuilder();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        int threadTotal = 2;
        final Semaphore semaphore = new Semaphore(threadTotal);
        for (int i = 0; i < 5 ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    ss.append("A");
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception",e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try{
            countDownLatch.await(500, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }
        executorService.shutdown();

        return ss.toString();
    }
}
