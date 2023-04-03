package com.qc.domain;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.List;

@Data
@Accessors(chain = true)
public class CourseListVo {

    /**
     * APP首页必须要展示的
     * courseName,courseCount,teacherName,wallImage,coursePrice
     */
    private BigInteger id;
    private String courseName;
    private String courseCount;
    private String teacherNickName;
    private String teacherRealName;
    private String coursePrice;
    private WallImageVo wallImageVo;

}
