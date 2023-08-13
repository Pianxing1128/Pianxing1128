package com.qc.module.pointMailOrder.service;


import com.qc.module.pointMerchandise.entity.PointMerchandise;
import com.qc.module.pointMerchandiseReceiverInfo.entity.PointMerchandiseReceiverInfo;
import com.qc.module.pointMerchandiseReceiverInfo.service.PointMerchandiseReceiverInfoService;
import com.qc.module.pointMerchandiseSenderInfo.entity.PointMerchandiseSenderInfo;
import com.qc.module.pointMerchandiseSenderInfo.service.PointMerchandiseSenderInfoService;
import com.qc.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
public class BasePointMailService {

    private PointMerchandiseReceiverInfoService pointMerchandiseReceiverInfoService;
    private PointMerchandiseSenderInfoService pointMerchandiseSenderInfoService;
    private PointMailOrderService pointMailOrderService;

    @Autowired
    public BasePointMailService(PointMerchandiseReceiverInfoService pointMerchandiseReceiverInfoService,
                                PointMerchandiseSenderInfoService pointMerchandiseSenderInfoService,
                                PointMailOrderService pointMailOrderService){
        this.pointMerchandiseReceiverInfoService = pointMerchandiseReceiverInfoService;
        this.pointMerchandiseSenderInfoService = pointMerchandiseSenderInfoService;
        this.pointMailOrderService = pointMailOrderService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createMailingOrder(BigInteger userId, BigInteger orderNumber, PointMerchandise pointMerchandise, Integer purchaseMerchandiseNumber) {
      //拿到收货人的详情
        PointMerchandiseReceiverInfo pointMerchandiseReceiverInfo = pointMerchandiseReceiverInfoService.getByUserId(userId);
        if(BaseUtils.isEmpty(pointMerchandiseReceiverInfo)){
           throw new RuntimeException("Acquiring pointMerchandiseReceiverInfo fails");
        }
        //拿到发货人的详情
       PointMerchandiseSenderInfo pointMerchandiseSenderInfo = pointMerchandiseSenderInfoService.getByMerchandiseId(pointMerchandise.getId());
        if(BaseUtils.isEmpty(pointMerchandiseSenderInfo)){
            throw new RuntimeException("Acquiring pointMerchandiseSenderInfo fails");
        }
       //创建邮寄订单
        pointMailOrderService.edit(userId,orderNumber,pointMerchandise,purchaseMerchandiseNumber);

   }
}
