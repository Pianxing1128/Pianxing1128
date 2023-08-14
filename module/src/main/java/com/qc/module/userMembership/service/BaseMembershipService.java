//package com.qc.module.membershipUser.service;
//
//import com.qc.module.pointMerchandise.entity.PointMerchandise;
//import com.qc.module.userMembership.entity.UserMembership;
//import com.qc.module.userMembership.service.UserMemberShipService;
//import com.qc.utils.BaseUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigInteger;
//
//@Service
//@Slf4j
//public class BaseMembershipService {
//
//    private UserMemberShipService userMemberShipService;
//    @Autowired
//    public BaseMembershipService(UserMemberShipService userMemberShipService){
//        this.userMemberShipService = userMemberShipService;
//    }
//    @Transactional(rollbackFor = Exception.class)
//    public void changeExpiredTimeForConsumption(BigInteger userId, PointMerchandise pointMerchandise, Integer merchandiseNumber) {
//
//        //购买24小时会员,从商品表里调取时间,根据购买数量，算出需要增加的时间
//        Integer addedTime = pointMerchandise.getMerchandiseDuration() * merchandiseNumber;
//
//        //根据userId查到去 用户会员时间表 当前用户的会员时间 如果查不到就在 MembershipUser 表里新增数据
//        UserMembership userMembership  = userMemberShipService.getByUserId(userId);
//        if(BaseUtils.isEmpty(userMembership)){
//            Integer membershipExpiredTime = 0;
//            membershipExpiredTime = addedTime;
//            userMemberShipService.edit(userId,membershipExpiredTime);
//        }else{
//            Integer membershipExpiredTime = userMembership.getMembershipExpiredTime();
//            // 当前过期时间戳+需要增加的时间戳，更新用户会员时间表里的过期时间
//            membershipExpiredTime = membershipExpiredTime + addedTime;
//            userMemberShipService.edit(userId,membershipExpiredTime);
//        }
//
//    }
//}
