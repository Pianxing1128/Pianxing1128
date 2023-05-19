package com.qc.module.appIndexBanner.mapper;

import com.qc.module.appIndexBanner.entity.AppIndexBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface AppIndexBannerMapper {

    int insert(@Param("appIndexBanner") AppIndexBanner appIndexBanner);

    int update(@Param("appIndexBanner") AppIndexBanner appIndexBanner);

    @Select("select * from app_index_banner where id =#{id} and is_deleted=0")
    AppIndexBanner getById(@Param("id") BigInteger id);

    @Select("select * from app_index_banner where id =#{id}")
    AppIndexBanner extractById(@Param("id") BigInteger id);

    @Update("update app_index_banner set is_deleted=1 and update_time = #{updateTime} where id =#{id}")
    int delete(@Param("id")BigInteger id,@Param("updateTime")Integer updateTime);

    @Select("select * from app_index_banner where is_deleted=0")
    List<AppIndexBanner> getIndexBannerList();


    List<AppIndexBanner> extractIndexBannerList(@Param("begin") int begin, @Param("size") Integer pageSize,@Param("bannerName") String bannerName);

    @Select("select count(*) from app_index_banner")
    Integer extractTotal();
}




