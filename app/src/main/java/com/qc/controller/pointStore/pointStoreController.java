package com.qc.controller.pointStore;

import com.qc.module.membershipUser.service.MembershipUserService;
import com.qc.module.pointMerchandise.entity.PointMerchandise;
import com.qc.module.pointMerchandise.service.PointMerchandiseService;
import com.qc.module.pointUser.service.PointUserService;
import com.qc.module.user.service.BasePointService;
import com.qc.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@Slf4j
public class pointStoreController {

    @Autowired
    private PointUserService pointUserService;

    @Autowired
    private PointMerchandiseService pointMerchandiseService;

    @Autowired
    private BasePointService basePointService;

//    @Autowired
//    private BaseMembershipService baseMembershipService;

    @Autowired
    private MembershipUserService membershipUserService;

    @RequestMapping("/point/merchandise/purchase")
    public Response pointMerchandisePurchase(BigInteger userId, BigInteger merchandiseId, Integer purchaseMerchandiseNumber){
        //先验证用户积分是否够用
        Integer userPoint = pointUserService.getUserPointByUserId(userId);
        //从商品表根据商品名称调出来 商品，如果没下架
        PointMerchandise pointMerchandise = pointMerchandiseService.getIsMarketableById(merchandiseId);
        Integer consumptionPoint = purchaseMerchandiseNumber * pointMerchandise.getMerchandisePoint();

        if(userPoint-consumptionPoint < 0){
            return new Response(4005);
        }
        //产生积分订单，积分变化记录，用户积分变化 + (1会员时间变化 or 2邮寄订单变化）
        try{

            basePointService.changePointForConsumption(userId,userPoint,consumptionPoint,pointMerchandise,purchaseMerchandiseNumber);
            //如果兑换的是会员时间
            if(pointMerchandise.getMerchandiseType()==0){
                membershipUserService.edit(userId,pointMerchandise,purchaseMerchandiseNumber);
            }
//            //如果购买是需要邮寄的实物
//            if(pointMerchandise.getMerchandiseType()==2){
//                baseMailingService.addMailingOrder(userId,merchandise,purchaseMerchandiseNumber);
//                return new Response(1001);
//            }
        }catch(Exception e){
            return new Response(4004);
        }
        return new Response(1001);
    }
}
