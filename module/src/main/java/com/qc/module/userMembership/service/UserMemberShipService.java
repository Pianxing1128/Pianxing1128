package com.qc.module.userMembership.service;

import com.qc.module.pointMerchandise.entity.PointMerchandise;
import com.qc.module.userMembership.entity.UserMembership;
import com.qc.module.userMembership.mapper.UserMembershipMapper;
import com.qc.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 用户会员表 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-09
 */
@Service
@Slf4j
public class UserMemberShipService {

    @Resource
    private UserMembershipMapper mapper;

    public BigInteger purchasingForMembership(BigInteger userId, PointMerchandise pointMerchandise, Integer purchaseMerchandiseNumber) {

        UserMembership userMembership = new UserMembership();
        userMembership.setUserId(userId);
        userMembership.setIsMembership(1);
        Integer addedTime = pointMerchandise.getMerchandiseDuration() * purchaseMerchandiseNumber;

        //能查到会员存在，就做更新
        UserMembership oldUserMembership = mapper.getByUserId(userId);
        if(!BaseUtils.isEmpty(oldUserMembership)){
            Integer expiredTime = oldUserMembership.getMembershipExpiredTime()+addedTime;
            userMembership.setMembershipExpiredTime(expiredTime);
            mapper.update(userMembership);
            return oldUserMembership.getId();
        }

        int now = BaseUtils.currentSeconds();
        userMembership.setMembershipExpiredTime(now+addedTime);
        userMembership.setCreateTime(now);
        mapper.insert(userMembership);
        return userMembership.getId();
    }

    public Integer getIsMembershipByUserId(BigInteger userId) {
        return mapper.getIsMembershipByUserId(userId);
    }
}
