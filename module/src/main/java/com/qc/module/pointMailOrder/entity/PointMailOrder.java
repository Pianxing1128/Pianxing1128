package com.qc.module.pointMailOrder.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 商品邮寄订单
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Data
@Accessors(chain = true)
public class PointMailOrder{

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
     * 商品名称
     */
    private String merchandiseName;

    /**
     * 商品数量
     */
    private Integer merchandiseNumber;

    /**
     * 发货人名称
     */
    private String senderName;

    /**
     * 发货人地址
     */
    private String senderAddress;

    /**
     * 发货人电话
     */
    private BigInteger senderPhone;

    /**
     * 发货地邮编
     */
    private Integer senderPostCode;

    /**
     * 收货人名称
     */
    private String receiverName;

    /**
     * 收货人地址
     */
    private String receiverAddress;

    /**
     * 收货人电话
     */
    private BigInteger receiverPhone;

    /**
     * 收货地邮编
     */
    private Integer receiverPostCode;

    /**
     * 更新时间
     */
    private Integer updateTime = BaseUtils.currentSeconds();

    /**
     * 创建时间
     */
    private Integer createTime;


}
