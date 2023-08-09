package com.qc.module.pointMerchandise.mapper;

import com.qc.module.pointMerchandise.entity.PointMerchandise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

/**
 * <p>
 * 积分商品商城 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-09
 */
@Mapper
public interface PointMerchandiseMapper {

    int insert(@Param("pointMerchandise") PointMerchandise pointMerchandise);

    int update(@Param("pointMerchandise") PointMerchandise pointMerchandise);

    @Select("select * from point_merchandise where id = #{id},is_deleted=0")
    PointMerchandise getById(@Param("id") BigInteger id);

    @Select("select * from point_merchandise where id=#{id}")
    PointMerchandise extractById(@Param("id")BigInteger id);

    @Update("update point_merchandise set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);

    @Select("select * from point_merchandise where id = #{merchandiseId} and is_marketable =1 and is_deleted=0")
    PointMerchandise getIsMarketableById(BigInteger merchandiseId);
}
