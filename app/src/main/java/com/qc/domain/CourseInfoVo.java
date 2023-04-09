package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CourseInfoVo {
    /**
     * 课程详情里必须要展示的
     * courseName,courseCount,teacherName(user表里的nickname),teacherIntro,courseImages,coursePrice
     * 课程里可以暂时没有的
     * courseSubName,courseTime
     */

    private String courseName;
    private String courseSubName;
    private String courseCount;
    private String courseTime;
    private String courseIntro;
    private String teacherName;
    private String teacherIntro;
    private List<ImageVo> imagesVo;
    private String coursePrice;

}
