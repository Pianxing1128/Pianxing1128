package com.qc.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class NewCourse {
    private BigInteger id;
    private String courseName;
    private String courseCount;
    private String coursePrice;
    private NewTeacher newTeacher;
}
