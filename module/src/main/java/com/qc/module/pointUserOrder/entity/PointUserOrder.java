package com.qc.module.pointUserOrder.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 用户积分订单
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Data
@Accessors(chain = true)
public class PointUserOrder {

    private BigInteger id;

    /**
     * 用户id
     */
    private BigInteger userId;

    /**
     * 订单号
     */
    private BigInteger orderNumber;

    /**
     * 供应商名称
     */
    private String supplierName;

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
     * 商品数量
     */
    private Integer merchandiseNumber;

    /**
     * 商品总额
     */
    private Integer consumptionPointAmount;

    /**
     * 订单地址
     */
    private String orderAddress;

    /**
     * 支付时间
     */
    private Integer purchasedTime;

    /**
     * 更新时间
     */
    private Integer updateTime = BaseUtils.currentSeconds();

    /**
     * 创建时间
     */
    private Integer createTime;


}
