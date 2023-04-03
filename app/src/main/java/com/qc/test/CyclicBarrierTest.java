package com.qc.test;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    private static class Worker extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Worker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            super.run();

            try {
                    System.out.println(Thread.currentThread().getName() + "开始等待其他线程");
                    int numberWaiting = cyclicBarrier.getNumberWaiting();
                    System.out.println("目前阻塞的线程数量为:" + (numberWaiting + 1));
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                    // 工作线程开始处理，这里用Thread.sleep()来模拟业务处理
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

    }

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 1; i <= 3; i++) {
            System.out.println("创建工作线程" + i);
            Worker worker = new Worker(cyclicBarrier);
            worker.start();
        }
    }
}
