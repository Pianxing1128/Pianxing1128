package com.qc.module.course.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class CourseTag {

    private BigInteger id;
    private String tag;
    private Integer isDeleted;
}
