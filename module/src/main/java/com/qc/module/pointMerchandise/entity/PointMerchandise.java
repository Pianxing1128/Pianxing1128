package com.qc.module.pointMerchandise.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 积分商品商城
 * </p>
 *
 * @author qc1128
 * @since 2023-08-09
 */
@Data
@Accessors(chain = true)
public class PointMerchandise {

    private BigInteger id;

    /**
     * 商品名称
     */
    private String merchandiseName;

    /**
     * 商品类型
     */
    private Integer merchandiseType;

    /**
     * 商品积分
     */
    private Integer merchandisePoint;


    /**
     * 商品持续时间
     */
    private Integer merchandiseDuration;
    /**
     * 是否在架
     */
    private Integer isMarketable;
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
