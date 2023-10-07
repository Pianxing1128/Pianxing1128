package com.qc.module.userRoleMenuRelation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-07-29
 */
@Mapper
public interface UserRoleMenuRelationMapper{

    @Select("select menu_id from user_role_menu_relation where role_id = #{roleId} and is_deleted=0")
    List<BigInteger> getMenuIdByRoleId(BigInteger roleId);
}
