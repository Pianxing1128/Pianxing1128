package com.qc.module.userRoleRelation.service;

import com.qc.module.userRoleRelation.mapper.UserRoleRelationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-07-29
 */
@Service
public class UserRoleRelationService {

    @Resource
    private UserRoleRelationMapper mapper;

    public BigInteger getRoleIdByUserId(BigInteger userId) {
        return mapper.getRoleIdByUserId(userId);
    }
}
