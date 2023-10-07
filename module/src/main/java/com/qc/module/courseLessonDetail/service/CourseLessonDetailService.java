package com.qc.module.courseLessonDetail.service;

import com.qc.module.courseLessonDetail.entity.CourseLessonDetail;
import com.qc.module.courseLessonDetail.mapper.CourseLessonDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 课程节课详情表 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-13
 */
@Service
public class CourseLessonDetailService{

    @Resource
    private CourseLessonDetailMapper mapper;

    public CourseLessonDetail getAvailableCourseLessonDetailById(BigInteger courseLessonDetailId) {
        return mapper.getAvailableCourseLessonDetailById(courseLessonDetailId);
    }
}
