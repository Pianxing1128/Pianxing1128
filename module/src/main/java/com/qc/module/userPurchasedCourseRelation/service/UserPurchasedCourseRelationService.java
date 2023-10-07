package com.qc.module.userPurchasedCourseRelation.service;

import com.qc.module.userPurchasedCourseRelation.entity.UserPurchasedCourseRelation;
import com.qc.module.userPurchasedCourseRelation.mapper.UserPurchasedCourseRelationMapper;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 用户购买课程表 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-13
 */
@Service
public class UserPurchasedCourseRelationService {

    @Resource
    private UserPurchasedCourseRelationMapper mapper;


    public List<BigInteger> getCourseIdByUserId(BigInteger userId) {
        return mapper.getCourseIdByUserId(userId);
    }

    public BigInteger edit(BigInteger userId, BigInteger courseId) {

        UserPurchasedCourseRelation userPurchasedCourseRelation = new UserPurchasedCourseRelation();
        userPurchasedCourseRelation.setUserId(userId);
        userPurchasedCourseRelation.setCourseId(courseId);
        userPurchasedCourseRelation.setCreateTime(BaseUtils.currentSeconds());
        mapper.insert(userPurchasedCourseRelation);
        return userPurchasedCourseRelation.getId();
    }
}
