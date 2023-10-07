package com.qc.module.userPurchasedCourseRelation.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 用户购买课程表
 * </p>
 *
 * @author qc1128
 * @since 2023-08-13
 */
@Data
@Accessors(chain = true)
public class UserPurchasedCourseRelation {

    private BigInteger id;

    /**
     * 用户id
     */
    private BigInteger userId;

    /**
     * 购买课程id
     */
    private BigInteger courseId;

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
