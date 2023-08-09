package com.qc.module.user.service;

import com.qc.module.pointMerchandise.entity.PointMerchandise;
import com.qc.module.pointUser.service.PointUserService;
import com.qc.module.pointUserChangeRecord.service.impl.PointUserChangeRecordService;
import com.qc.module.pointUserOrder.service.PointUserOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@Slf4j
public class BasePointService {

    private PointUserService pointUserService;
    private PointUserOrderService pointUserOrderService;
    private PointUserChangeRecordService pointUserChangeRecordService;
    @Autowired
    public BasePointService(PointUserService pointUserService,PointUserOrderService pointUserOrderService,PointUserChangeRecordService pointUserChangeRecordService) {
        this.pointUserService = pointUserService;
        this.pointUserOrderService = pointUserOrderService;
        this.pointUserChangeRecordService = pointUserChangeRecordService;
    }



    @Transactional(rollbackFor = Exception.class)
    public Integer registerForGiftPoint(BigInteger userId) {
        //产生积分订单
        Integer merchandisePoint = 100;
        BigInteger orderId =pointUserOrderService.edit(userId,merchandisePoint,0, 0);

        //产生积分变化记录
        Integer preConsumptionPoint = 0;
        Integer consumptionPointAmount = merchandisePoint;
        Integer afterConsumptionPoint = preConsumptionPoint + consumptionPointAmount;
        String consumptionDescription = "注册用户送积分";
        BigInteger pointChangeRecordId = pointUserChangeRecordService.edit(userId, orderId, consumptionDescription, preConsumptionPoint, consumptionPointAmount, afterConsumptionPoint);
        log.info(String.valueOf(pointChangeRecordId));

        //新增用户积分
        pointUserService.edit(userId,afterConsumptionPoint);
        return afterConsumptionPoint;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer changePointForConsumption(BigInteger userId, Integer userPoint,Integer consumptionPoint,PointMerchandise pointMerchandise,Integer purchaseMerchandiseNumber){

        //产生积分订单
        Integer merchandisePoint = pointMerchandise.getMerchandisePoint();
        BigInteger orderId =pointUserOrderService.edit(userId,merchandisePoint,purchaseMerchandiseNumber,consumptionPoint);

        //产生积分变化记录
        Integer preConsumptionPoint = userPoint;
        Integer afterConsumptionPoint = preConsumptionPoint - consumptionPoint;
        String consumptionDescription = pointMerchandise.getMerchandiseName();
        BigInteger pointChangeRecordId = pointUserChangeRecordService.edit(userId, orderId, consumptionDescription, preConsumptionPoint, consumptionPoint, afterConsumptionPoint);
        log.info(String.valueOf(pointChangeRecordId));

        //更新用户积分
        pointUserService.edit(userId,merchandisePoint);
        return afterConsumptionPoint;
    }
}
