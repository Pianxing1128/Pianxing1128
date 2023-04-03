package com.qc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TeacherVo {

    private BigInteger id;
    private BigInteger userId;
    private String realName;
    private String enrollmentTime;
    private String createTime;
    private String updateTime;
    private Integer isDeleted;
}
