package com.qc.module.pointUserChangeRecord.service.impl;

import com.qc.module.pointUserChangeRecord.entity.PointUserChangeRecord;
import com.qc.module.pointUserChangeRecord.mapper.PointUserChangeRecordMapper;
import com.qc.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 用户积分变化 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Service
@Slf4j
public class PointUserChangeRecordService {

    @Resource
    private PointUserChangeRecordMapper mapper;

    public BigInteger edit(BigInteger userId, BigInteger orderId, String consumptionDescription, Integer preConsumptionPoint, Integer consumptionPointAmount, Integer afterConsumptionPoint) {

        PointUserChangeRecord pointUserChangeRecord = new PointUserChangeRecord();
        pointUserChangeRecord.setUserId(userId);
        pointUserChangeRecord.setOrderId(orderId);
        pointUserChangeRecord.setConsumptionDescription(consumptionDescription);
        pointUserChangeRecord.setPreConsumptionPoint(preConsumptionPoint);
        pointUserChangeRecord.setConsumptionPointsAmount(consumptionPointAmount);
        pointUserChangeRecord.setAfterConsumptionPoint(afterConsumptionPoint);
        pointUserChangeRecord.setCreateTime(BaseUtils.currentSeconds());
        mapper.insert(pointUserChangeRecord);

        return pointUserChangeRecord.getId();

    }
}
