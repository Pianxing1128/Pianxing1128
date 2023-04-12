package com.qc.domain.course;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.List;

@Data
@Accessors(chain = true)
public class CourseVo {


    private BigInteger id;
    private BigInteger teacherId;
    private String teacherAvatar;
    private String courseName;
    private String courseSubName;
    private String courseCount;
    private String courseTime;
    private String courseIntro;
    private String realName;
    private String nickName;
    private String teacherIntro;
    private List<String> courseImages;
    private String coursePrice;
    private Integer weight;
    private String createTime;
    private String updateTime;
    private Integer isDeleted;

}
