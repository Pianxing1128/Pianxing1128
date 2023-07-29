package com.qc.module.user.service;

import com.qc.module.user.mapper.UserMenuMapper;
import com.qc.module.userRoleMenuRelation.service.UserRoleMenuRelationService;
import com.qc.module.userRoleRelation.service.UserRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class UserMenuService {

    @Resource
    private UserMenuMapper mapper;

    private UserRoleRelationService userRoleRelationService;

    private UserRoleMenuRelationService userRoleMenuRelationService;

    @Autowired
    public UserMenuService(UserRoleRelationService userRoleRelationService,UserRoleMenuRelationService userRoleMenuRelationService){
        this.userRoleMenuRelationService = userRoleMenuRelationService;
        this.userRoleRelationService = userRoleRelationService;
    }

    public List<String> getPermissionsByUserId(BigInteger userId) {
        BigInteger roleIdByUserId = userRoleRelationService.getRoleIdByUserId(userId);
        List<BigInteger> menuIdListByRoleId = userRoleMenuRelationService.getMenuIdByRoleId(roleIdByUserId);
        StringBuilder ss = new StringBuilder();
        for(BigInteger m:menuIdListByRoleId){
            ss.append(m+",");
        }
        int len = ss.length();
        if(len==0){
            return null;
        }
        ss.delete(len-1,len);
        String menuIds = ss.toString();
        return mapper.getPermissionsByUserId(menuIds);
    }
}
