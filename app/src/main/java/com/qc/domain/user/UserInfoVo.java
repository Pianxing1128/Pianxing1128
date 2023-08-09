package com.qc.domain.user;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 8个属性
     */
    private BigInteger id;
    private String userAccount;
    private String avatar;
    private Integer gender;
    private String nickName;
    private String email;
    private String birthday;
    private String userIntro;
    private Integer userCurrentPoint;

}
