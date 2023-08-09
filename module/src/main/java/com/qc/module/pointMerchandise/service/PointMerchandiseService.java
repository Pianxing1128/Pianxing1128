package com.qc.module.pointMerchandise.service;

import com.qc.module.pointMerchandise.entity.PointMerchandise;
import com.qc.module.pointMerchandise.mapper.PointMerchandiseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 积分商品商城 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-09
 */
@Service
@Slf4j
public class PointMerchandiseService {

    @Resource
    private PointMerchandiseMapper mapper;

    public PointMerchandise getIsMarketableById(BigInteger merchandiseId) {
        return mapper.getIsMarketableById(merchandiseId);
    }
}
