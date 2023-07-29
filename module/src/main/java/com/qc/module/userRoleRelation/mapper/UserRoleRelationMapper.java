package com.qc.module.userRoleRelation.mapper;

import com.qc.module.userRoleRelation.entity.UserRoleRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-07-29
 */
@Mapper
public interface UserRoleRelationMapper{

    @Select("select role_id from december.user_role_relation where user_id=#{userId} and is_deleted=0")
    BigInteger getRoleIdByUserId(BigInteger userId);

    int insert(@Param("userRoleRelation") UserRoleRelation userRoleRelation);

    int update(@Param("userRoleRelation") UserRoleRelation userRoleRelation);
}
