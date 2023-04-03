package com.qc.test;

public class ViolateTest {
    /**
     * 变量上加了volatile关键字：
     * 为了验证volatile的可见性。
     */
    public static class Test2 {
        volatile int number = 0;

        public void add(){
            this.number = 10;
        }

    public static void main(String[] args) {
        Test2 test2 = new Test2();

        //创建第一个线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"开始执行时，number = "+test2.number);

            try{
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }

            test2.add();//暂停3秒后，修改number的值。
            System.out.println(Thread.currentThread().getName()+"执行add()方法之后，number = "+test2.number);

        },"Thread_One").start();


        //第二个是main线程
        while (test2.number == 0){
            //由于变量number上加了volatile关键字，
            // 使得第二个main线程可以监测到number值的改变，从而跳出了循环。
        }
        System.out.println(Thread.currentThread().getName()+"程序运行结束！");
        }
    }
}
