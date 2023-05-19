package com.qc.domain.appIndexTag;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class AppIndexTagVo {

    private BigInteger id;
    private String showTagName;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;
}
