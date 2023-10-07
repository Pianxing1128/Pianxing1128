package com.qc.module.userRoleMenuRelation.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
public class UserRoleMenuRelation implements Serializable {

    private BigInteger id;

    /**
     * 角色表的编号
     */
    private BigInteger roleId;

    /**
     * 菜单表的编号
     */
    private BigInteger menuId;

    private Integer updateTime;

    private Integer createTime;

    private Integer isDeleted;


}
