package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class UserVo {

    private BigInteger id;
    private String avatar;
    private String nickName;
    private Integer gender;
    private String email;
    private String userIntro;
    private String birthday;
    private String lastLoginTime;
    private String lastLoginIp;
    private String registerIp;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;

}
