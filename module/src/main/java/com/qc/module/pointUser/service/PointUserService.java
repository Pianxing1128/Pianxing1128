package com.qc.module.pointUser.service;

import com.qc.module.pointUser.entity.PointUser;
import com.qc.module.pointUser.mapper.PointUserMapper;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 用户积分 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Service
public class PointUserService{


    @Resource
    private PointUserMapper mapper;

    public Integer getUserPointByUserId(BigInteger userId) {
        return mapper.getUserPointByUserId(userId);
    }

    public BigInteger edit(BigInteger userId,Integer afterConsumptionPoint) {

        PointUser pointUser = new PointUser();
        pointUser.setUserId(userId);
        pointUser.setPoint(afterConsumptionPoint);

        //查看userId是否能查出来，能则做update
        PointUser oldPointUser = mapper.getByUserId(userId);
        if(!BaseUtils.isEmpty(oldPointUser)){
            pointUser.setId(oldPointUser.getId());
            mapper.update(pointUser);
            return oldPointUser.getId();
        }
        pointUser.setCreateTime(BaseUtils.currentSeconds());
        int i = mapper.insert(pointUser);
        if(i<=0){
            throw new RuntimeException("Inserting pointUser fails!");
        }
        return pointUser.getId();
    }

    public Integer getPointByUserId(BigInteger userId) {
        return mapper.getUserPointByUserId(userId);
    }
}
