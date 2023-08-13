package com.qc.module.pointMailOrder.service;

import com.qc.module.pointMailOrder.entity.PointMailOrder;
import com.qc.module.pointMailOrder.mapper.PointMailOrderMapper;
import com.qc.module.pointMerchandise.entity.PointMerchandise;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 商品邮寄订单 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Service
public class PointMailOrderService{

    @Resource
    private PointMailOrderMapper mapper;

    public void edit(BigInteger userId, BigInteger orderNumber, PointMerchandise pointMerchandise,Integer purchaseMerchandiseNumber){
        PointMailOrder pointMailOrder = new PointMailOrder();
        pointMailOrder.setOrderNumber(orderNumber);
        pointMailOrder.setUserId(userId);
        pointMailOrder.setMerchandiseName(pointMerchandise.getMerchandiseName());
        pointMailOrder.setMerchandiseNumber(purchaseMerchandiseNumber);
        mapper.insert(pointMailOrder);

    }

}
