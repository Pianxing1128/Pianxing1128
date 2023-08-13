package com.qc.module.pointMerchandiseSenderInfo.service;

import com.qc.module.pointMerchandiseSenderInfo.entity.PointMerchandiseSenderInfo;
import com.qc.module.pointMerchandiseSenderInfo.mapper.PointMerchandiseSenderInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 发货人信息 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Service
public class PointMerchandiseSenderInfoService {

    @Resource
    private PointMerchandiseSenderInfoMapper mapper;

    public PointMerchandiseSenderInfo getByMerchandiseId(BigInteger merchandiseId) {
        return mapper.getByMerchandiseId(merchandiseId);
    }
}
