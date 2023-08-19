package com.qc.module.pointUserOrder.service;

import com.qc.module.pointUserOrder.entity.PointUserOrder;
import com.qc.module.pointUserOrder.mapper.PointUserOrderMapper;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 用户积分订单 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Service
public class PointUserOrderService{

    @Resource
    private PointUserOrderMapper mapper;

    public BigInteger edit(BigInteger userId, Integer merchandisePoint,Integer purchaseMerchandiseNumber,Integer consumptionPoint,String merchandiseName) {

        PointUserOrder pointUserOrder = new PointUserOrder();
        pointUserOrder.setUserId(userId);
        pointUserOrder.setMerchandisePoint(merchandisePoint);
        pointUserOrder.setMerchandiseName(merchandiseName);
        pointUserOrder.setMerchandiseNumber(purchaseMerchandiseNumber);
        pointUserOrder.setConsumptionPointAmount(consumptionPoint);
        pointUserOrder.setCreateTime(BaseUtils.currentSeconds());
        int i = mapper.insert(pointUserOrder);
        if(i<=0){
            throw new RuntimeException("Inserting pointUserOrder fails!");
        }
        return pointUserOrder.getId();
    }

    public Integer getMerchandisePointByOrderId(BigInteger orderId) {
        return mapper.getMerchandisePointByOrderId(orderId);
    }

    public BigInteger getOrderNumberById(BigInteger orderId) {
        return mapper.getOrderNumberById(orderId);
    }
}
