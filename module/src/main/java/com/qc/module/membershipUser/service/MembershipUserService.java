package com.qc.module.membershipUser.service;

import com.qc.module.membershipUser.entity.MembershipUser;
import com.qc.module.membershipUser.mapper.MembershipUserMapper;
import com.qc.module.pointMerchandise.entity.PointMerchandise;
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
public class MembershipUserService {

    @Resource
    private MembershipUserMapper mapper;

    public BigInteger edit(BigInteger userId, PointMerchandise pointMerchandise, Integer purchaseMerchandiseNumber) {

        MembershipUser membershipUser = new MembershipUser();
        membershipUser.setUserId(userId);
        membershipUser.setIsMembership(1);
        Integer addedTime = pointMerchandise.getMerchandiseDuration() * purchaseMerchandiseNumber;

        //能查到会员存在，就做更新
        MembershipUser oldMembershipUser = mapper.getByUserId(userId);
        if(!BaseUtils.isEmpty(oldMembershipUser)){
            Integer expiredTime = oldMembershipUser.getMembershipExpiredTime()+addedTime;
            membershipUser.setMembershipExpiredTime(expiredTime);
            mapper.update(membershipUser);
            return oldMembershipUser.getId();
        }

        int now = BaseUtils.currentSeconds();
        membershipUser.setMembershipExpiredTime(now+addedTime);
        membershipUser.setCreateTime(now);
        mapper.insert(membershipUser);
        return membershipUser.getId();
    }
}
