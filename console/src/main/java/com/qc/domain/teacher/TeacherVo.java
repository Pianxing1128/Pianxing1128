package com.qc.domain.teacher;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class TeacherVo {

    private BigInteger id;
    private BigInteger userId;
    private String realName;
    private String enrollmentTime;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;
}
