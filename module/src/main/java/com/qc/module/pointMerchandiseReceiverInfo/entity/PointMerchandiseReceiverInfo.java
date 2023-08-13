package com.qc.module.pointMerchandiseReceiverInfo.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 收货人信息
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PointMerchandiseReceiverInfo{

    private BigInteger id;

    /**
     * 用户id
     */
    private BigInteger userId;

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

    /**
     * 是否删除
     */
    private Integer isDeleted = 0;


}
