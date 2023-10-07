package com.qc.domain.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class UserVo {

    private BigInteger id;
    private String userAccount;
    private String avatar;
    private String nickName;
    private Integer gender;
    private String email;
    private String userIntro;
    private String birthday;
    private String lastLoginTime;
    private String lastLoginIp;
    private String registerIp;
    private Integer isBan;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;
    private String token;
}
