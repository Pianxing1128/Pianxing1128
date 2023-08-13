package com.qc.module.pointMerchandiseSenderInfo.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 发货人信息
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Data
@Accessors(chain = true)
public class PointMerchandiseSenderInfo {

    private BigInteger id;

    /**
     * 商品id
     */
    private BigInteger merchandiseId;

    /**
     * 商品名称
     */
    private String merchandiseName;

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
     * 更新时间
     */
    private Integer updateTime = BaseUtils.currentSeconds();

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 创建时间
     */
    private Integer isDeleted = 0;


}
