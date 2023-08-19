package com.qc.module.pointMailOrder.mapper;

import com.qc.module.pointMailOrder.entity.PointMailOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

/**
 * <p>
 * 商品邮寄订单 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Mapper
public interface PointMailOrderMapper{

    int insert(@Param("pointMailOrder") PointMailOrder pointMailOrder);

    int update(@Param("pointMailOrder") PointMailOrder pointMailOrder);

    @Select("select * from point_merchandise_receiver_info where id = #{id} and is_deleted=0")
    PointMailOrder getById(@Param("id") BigInteger id);

    @Select("select * from point_merchandise_receiver_info where id=#{id}")
    PointMailOrder extractById(@Param("id")BigInteger id);

    @Update("update point_merchandise_receiver_info  set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);

}
