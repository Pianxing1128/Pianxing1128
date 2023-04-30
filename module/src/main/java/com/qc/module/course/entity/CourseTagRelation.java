package com.qc.module.course.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class CourseTagRelation {

    private BigInteger id;
    private BigInteger courseId;
    private BigInteger tagId;
    private Integer updateTime = BaseUtils.currentSeconds();
    private Integer createTime;
    private Integer isDeleted = 0;
}
