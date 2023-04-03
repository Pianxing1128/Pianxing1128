package com.qc.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 变量上加了volatile关键字 ，
 * 但 不能保证原子性 的 解决方式。
 */
 class Test2 {

    volatile int number = 0;

    //解决方式一：方法上加 synchronized 关键字
//    public synchronized void add() {
//        number++;
//    }
    public void add() {
        number++;
    }

    //解决方式二：如下
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addMyAtomic() {
        //每调用一次此方法，加个一。
        atomicInteger.getAndIncrement();
    }

}
public class SynchronizeTest{
    public static void main(String[] args) {
        Test2 test2 = new Test2();


        //创建10个线程
        for (int i = 0;i < 10;i++){
            new Thread(() -> {
                //每个线程执行1001次+1操作
                for (int j = 0;j<100;j++){
                    test2.add();//调用不能保证原子性的方法
                    test2.addMyAtomic();//调用可以保证原子性的方法。
                }
            },"Thread_"+(i+1)).start();
        }

        //如果正在运行的线程数>2个(除了main线程和GC线程以外，还有其他线程正在运行)
        while(Thread.activeCount() >2){
            Thread.yield();//礼让其他线程，暂不执行后续程序
        }

        System.out.println("执行 1000次 +1操作后，number = "+test2.number);
        System.out.println("执行 1000次 +1操作后，atomicInteger = "+test2.atomicInteger);

    }
}
