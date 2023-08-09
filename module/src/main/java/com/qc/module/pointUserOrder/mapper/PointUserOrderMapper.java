package com.qc.module.pointUserOrder.mapper;

import com.qc.module.pointUserOrder.entity.PointUserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;

/**
 * <p>
 * 用户积分订单 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Mapper
public interface PointUserOrderMapper {

    int insert(@Param("pointUserOrder") PointUserOrder pointUserOrder);

    int update(@Param("pointUserOrder")PointUserOrder pointUserOrder);

    @Select("select * from point_user_order where is_deleted=0 and id=#{id}")
    PointUserOrder getById(@Param("id") BigInteger id);

    @Select("select * from point_user_order where id=#{id}")
    PointUserOrder extractById(@Param("id")BigInteger id);

    @Select("select merchandise_point from point_user_order where order_id=#{orderId}")
    Integer getMerchandisePointByOrderId(BigInteger orderId);
}
