package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class CourseListVo {

    /**
     * APP首页必须要展示的
     * 第一张表course中的courseName,courseCount,coursePrice,wallImageVo
     * 第二张表teacher的realName
     * 第三张表user的nickName
     */
    private BigInteger id;
    private String courseName;
    private String courseCount;
    private String teacherNickName;
    private String teacherRealName;
    private String coursePrice;
    private WallImageVo wallImageVo;

}
