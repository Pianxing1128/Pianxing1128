package com.qc.module.membershipUser.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 用户会员表
 * </p>
 *
 * @author qc1128
 * @since 2023-08-09
 */
@Data
@Accessors(chain = true)
public class MembershipUser {

    private BigInteger id;

    /**
     * 用户id
     */
    private BigInteger userId;

    /**
     * 是否会员
     */
    private Integer isMembership;

    /**
     * 会员类型名称
     */
    private String membershipName;

    /**
     * 会员过期时间
     */
    private Integer membershipExpiredTime;

    /**
     * 更新时间
     */
    private Integer updateTime = BaseUtils.currentSeconds();

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 是否删除
     */
    private Integer isDeleted = 0;


}
