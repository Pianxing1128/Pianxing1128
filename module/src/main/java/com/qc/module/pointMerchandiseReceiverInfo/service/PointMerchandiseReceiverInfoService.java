package com.qc.module.pointMerchandiseReceiverInfo.service;


import com.qc.module.pointMerchandiseReceiverInfo.entity.PointMerchandiseReceiverInfo;
import com.qc.module.pointMerchandiseReceiverInfo.mapper.PointMerchandiseReceiverInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 收货人信息 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Service
public class PointMerchandiseReceiverInfoService {

    @Resource
    private PointMerchandiseReceiverInfoMapper mapper;

    public PointMerchandiseReceiverInfo getByUserId(BigInteger userId) {
        return mapper.getByUserId(userId);
    }
}
