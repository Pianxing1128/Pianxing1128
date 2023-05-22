package com.qc.domain.appIndexTagIdRelation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class AppIndexTagIdRelationVo {

    private BigInteger id;
    private BigInteger showTagId;
    private BigInteger tagId;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;
}
