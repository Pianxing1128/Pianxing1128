package com.qc.module.userShareType.service;

import com.qc.module.userShareType.entity.UserShareType;
import com.qc.module.userShareType.mapper.UserShareTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * <p>
 * 用户分享类型 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-08-15
 */
@Service
public class UserShareTypeService{

    @Resource
    private UserShareTypeMapper mapper;

    public UserShareType getById(BigInteger shareId) {
        return mapper.getById(shareId);

    }
}
