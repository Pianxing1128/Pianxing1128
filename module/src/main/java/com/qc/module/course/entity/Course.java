package com.qc.module.course.entity;

import com.qc.module.teacher.entity.Teacher;
import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;


@Data
@Accessors(chain = true)
public class Course {

        private BigInteger id;
        private BigInteger teacherId;
        private String courseName;
        private String courseSubName;
        private String courseCount;
        private String courseTime;
        private String courseImage;
        private String courseIntro;
        private Integer coursePrice;
        private Integer weight;
        private Integer isVip;
        private Integer isMarketable = 1;
        private Integer purchasedTotal;
        private Integer updateTime = BaseUtils.currentSeconds();
        private Integer createTime;
        private Integer isDeleted = 0;
        private Teacher teacher;

}