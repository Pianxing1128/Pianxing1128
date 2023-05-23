package com.qc.domain.courseTag;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class TagInfoVo {

    private BigInteger id;
    private String tag;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;

}
