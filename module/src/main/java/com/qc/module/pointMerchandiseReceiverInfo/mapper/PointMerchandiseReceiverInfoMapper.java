package com.qc.module.pointMerchandiseReceiverInfo.mapper;

import com.qc.module.course.entity.Course;
import com.qc.module.pointMerchandiseReceiverInfo.entity.PointMerchandiseReceiverInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

/**
 * <p>
 * 收货人信息 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-10
 */
@Mapper
public interface PointMerchandiseReceiverInfoMapper{

    int insert(@Param("pointMerchandiseReceiverInfo") PointMerchandiseReceiverInfo pointMerchandiseReceiverInfo);

    int update(@Param("pointMerchandiseReceiverInfo") PointMerchandiseReceiverInfo pointMerchandiseReceiverInfo);

    @Select("select * from point_merchandise_receiver_info where id = #{id} and is_deleted=0")
    Course getById(@Param("id") BigInteger id);

    @Select("select * from point_merchandise_receiver_info where id=#{id}")
    Course extractById(@Param("id")BigInteger id);

    @Update("update point_merchandise_receiver_info  set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);

    @Select("select * from point_merchandise_receiver_info where user_id=#{userId} and is_delelted = 0 ")
    PointMerchandiseReceiverInfo getByUserId(BigInteger userId);
}
