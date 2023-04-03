package com.qc.service;


import com.qc.mapper.CourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Slf4j
@Component
public class ThreadService {

    //希望在线程池中操作 但是不影响原有的主线程
    @Async("taskExecutor")
    public void updateCourse(CourseMapper mapper, BigInteger id) {

        try {
            Thread.sleep(5000);
            log.info("更新完成了...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
