package com.qc.domain.courseTagRelation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class CourseTagRelationVo {

    private BigInteger id;
    private BigInteger courseId;
    private BigInteger tagId;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;
}
