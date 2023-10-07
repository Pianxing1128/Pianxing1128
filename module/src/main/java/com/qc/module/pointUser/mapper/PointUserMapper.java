package com.qc.module.pointUser.mapper;

import com.qc.module.pointUser.entity.PointUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;

/**
 * <p>
 * 用户积分 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Mapper
public interface PointUserMapper {

    int insert(@Param("pointUser") PointUser pointUser);

    int update(@Param("pointUser")PointUser pointUser);

    @Select("select * from point_user where is_deleted=0 and id=#{id}")
    PointUser getById(@Param("id") BigInteger id);

    @Select("select * from point_user where id=#{id}")
    PointUser extractById(@Param("id")BigInteger id);

    @Select("select point from point_user where user_id =#{userId} and is_deleted =0")
    Integer getUserPointByUserId(BigInteger userId);

    @Select("select * from point_user where user_id= #{userId} and is_deleted=0")
    PointUser getByUserId(BigInteger userId);
}
