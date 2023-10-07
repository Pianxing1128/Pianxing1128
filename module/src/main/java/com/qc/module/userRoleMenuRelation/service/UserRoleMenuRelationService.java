package com.qc.module.userRoleMenuRelation.service;

import com.qc.module.userRoleMenuRelation.mapper.UserRoleMenuRelationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-07-29
 */
@Service
public class UserRoleMenuRelationService{

    @Resource
    private UserRoleMenuRelationMapper mapper;

    public List<BigInteger> getMenuIdByRoleId(BigInteger roleId) {
        return mapper.getMenuIdByRoleId(roleId);
    }



}
