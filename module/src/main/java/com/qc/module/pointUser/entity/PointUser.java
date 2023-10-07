package com.qc.module.pointUser.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 用户积分
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Data
@Accessors(chain = true)
public class PointUser {

    private BigInteger id;

    /**
     * 用户id
     */
    private BigInteger userId;

    /**
     * 用户当前积分
     */
    private Integer point;

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
