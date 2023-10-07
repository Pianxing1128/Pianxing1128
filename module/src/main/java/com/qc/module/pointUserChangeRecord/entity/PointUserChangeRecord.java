package com.qc.module.pointUserChangeRecord.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 用户积分变化
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Data
@Accessors(chain = true)
public class PointUserChangeRecord {

    private BigInteger id;

    /**
     * 用户id
     */
    private BigInteger userId;

    /**
     * 订单id
     */
    private BigInteger orderId;

    /**
     * 消费描述
     */
    private String consumptionDescription;

    /**
     * 消费前积分
     */
    private Integer preConsumptionPoint;

    /**
     * 消费总额
     */
    private Integer consumptionPointsAmount;

    /**
     * 消费后积分
     */
    private Integer afterConsumptionPoint;

    /**
     * 更新时间
     */
    private Integer updateTime = BaseUtils.currentSeconds();

    /**
     * 创建时间
     */
    private Integer createTime;


}
