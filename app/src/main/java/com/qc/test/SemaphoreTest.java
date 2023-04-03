package com.qc.test;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) {
        //初始值为几？（就是限制几个线程同时执行，并行）
        Semaphore semaphore = new Semaphore(3,true);
        //模拟9个线程去抢执行权
        for (int i = 0; i <9; i++) {
            new Thread(new SemaphoreTask(semaphore,"线程"+i)).start();
        }
    }
}
@Slf4j
class SemaphoreTask extends Thread{

    Semaphore semaphore;
    public SemaphoreTask(Semaphore semaphore,String name){
        super(name);
        this.semaphore=semaphore;
    }
    public void run(){
        try {
            //tryAcquire尝试在500毫秒内获取执行权，并返回true/false反馈是否成功
            boolean b = semaphore.tryAcquire(500, TimeUnit.MILLISECONDS);

            //获取成功
            if(b){
                //执行任务
                log.info("{}线程在{}时间执行任务",Thread.currentThread().getName(),System.currentTimeMillis());
                Thread.sleep(200);
                //执行完任务需要释放权利，以供其他线程拿到执行权
                semaphore.release();
                //获取失败
            }else{
                //执行降级任务
                degrade();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 定义降级任务
     */
    private void degrade(){
        log.info("降级服务");
    }

}