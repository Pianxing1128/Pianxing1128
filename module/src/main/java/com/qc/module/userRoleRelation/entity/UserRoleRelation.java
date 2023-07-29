package com.qc.module.userRoleRelation.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 
 * </p>
 *
 * @author qc1128
 * @since 2023-07-29
 */
@Data
@Accessors(chain = true)
public class UserRoleRelation{

    private BigInteger id;
    /**
     * 用户编号
     */
    private BigInteger userId;

    /**
     * 角色编号
     */
    private BigInteger roleId;

    private Integer updateTime;

    private Integer createTime;

    private Integer isDeleted;


}
