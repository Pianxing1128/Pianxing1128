package com.qc.module.user.service;

import com.qc.module.course.entity.Course;
import com.qc.module.pointMerchandise.entity.PointMerchandise;
import com.qc.module.pointUser.service.PointUserService;
import com.qc.module.pointUserChangeRecord.service.impl.PointUserChangeRecordService;
import com.qc.module.pointUserOrder.service.PointUserOrderService;
import com.qc.module.userShareType.entity.UserShareType;
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
        BigInteger orderId =pointUserOrderService.edit(userId,merchandisePoint,0, 0,null);

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
    public BigInteger changePointForConsumption(BigInteger userId, Integer userPoint,Integer consumptionPoint,PointMerchandise pointMerchandise,Integer purchaseMerchandiseNumber){

        //产生积分订单
        Integer merchandisePoint = pointMerchandise.getMerchandisePoint();
        BigInteger orderId =pointUserOrderService.edit(userId,merchandisePoint,purchaseMerchandiseNumber,consumptionPoint,null);

        //产生积分变化记录
        Integer preConsumptionPoint = userPoint;
        Integer afterConsumptionPoint = preConsumptionPoint - consumptionPoint;
        String consumptionDescription = "积分兑换"+pointMerchandise.getMerchandiseName();
        BigInteger pointChangeRecordId = pointUserChangeRecordService.edit(userId, orderId, consumptionDescription, preConsumptionPoint, consumptionPoint, afterConsumptionPoint);
        log.info(String.valueOf(pointChangeRecordId));

        //更新用户积分
        pointUserService.edit(userId,merchandisePoint);
        BigInteger orderNumber = pointUserOrderService.getOrderNumberById(orderId);
        return orderNumber;
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger purchasingCourseForGiftPoint(BigInteger userId, Course course) {

        //产生积分订单,课程价钱除以100
        Integer merchandisePoint = Math.round(course.getCoursePrice()/100);
        BigInteger orderIdForPurchasingCourse =pointUserOrderService.edit(userId,merchandisePoint,1,0,course.getCourseName());

        //产生积分变化记录
        Integer preConsumptionPoint = pointUserService.getUserPointByUserId(userId);;
        Integer afterConsumptionPoint = preConsumptionPoint +merchandisePoint;
        String consumptionDescription = "购买" +course.getCourseName();
        BigInteger pointChangeRecordId = pointUserChangeRecordService.edit(userId, orderIdForPurchasingCourse, consumptionDescription, preConsumptionPoint, merchandisePoint, afterConsumptionPoint);
        log.info(String.valueOf(pointChangeRecordId));

        //更新用户积分
        pointUserService.edit(userId,afterConsumptionPoint);

        return orderIdForPurchasingCourse;
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger addPointForSingIn(BigInteger userId, Integer addedPoint) {

        //产生积分订单
        BigInteger orderIdForAddingPointForSingIn =pointUserOrderService.edit(userId,addedPoint,0,0,null);

        //产生积分变化记录
        Integer preConsumptionPoint = pointUserService.getUserPointByUserId(userId);;
        Integer afterConsumptionPoint = preConsumptionPoint +addedPoint;
        String consumptionDescription = "签到";
        BigInteger pointChangeRecordId = pointUserChangeRecordService.edit(userId, orderIdForAddingPointForSingIn, consumptionDescription, preConsumptionPoint, addedPoint, afterConsumptionPoint);
        log.info(String.valueOf(pointChangeRecordId));

        //更新用户积分
        pointUserService.edit(userId,afterConsumptionPoint);

        return orderIdForAddingPointForSingIn;
    }
    @Transactional(rollbackFor = Exception.class)
    public BigInteger addPointForSharing(BigInteger userId, BigInteger courseId, UserShareType userShareType) {
        //产生积分订单,用户增加100积分
        Integer merchandisePoint = 100;
        BigInteger orderId =pointUserOrderService.edit(userId,merchandisePoint,0,100,null);

        //产生积分变化记录
        Integer preConsumptionPoint = pointUserService.getUserPointByUserId(userId);;
        Integer afterConsumptionPoint = preConsumptionPoint + merchandisePoint;
        String consumptionDescription = userShareType.getShareName();
        BigInteger pointChangeRecordId = pointUserChangeRecordService.edit(userId, orderId, consumptionDescription, preConsumptionPoint, merchandisePoint, afterConsumptionPoint);
        log.info(String.valueOf(pointChangeRecordId));

        //更新用户积分
        BigInteger pointUserOrderId = pointUserService.edit(userId,merchandisePoint);

        return pointUserOrderId;
    }
}
