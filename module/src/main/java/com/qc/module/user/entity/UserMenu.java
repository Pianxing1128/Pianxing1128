package com.qc.module.user.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserMenu{
    private Integer id;
    private Integer pid;
    private Integer type;
    private String name;
    private String code;
    private Integer updateTime = BaseUtils.currentSeconds();
    private Integer createTime;
    private Integer isDeleted = 0;

}