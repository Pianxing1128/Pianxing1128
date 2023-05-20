package com.qc.module.appIndexTag.mapper;


import com.qc.module.appIndexTag.entity.AppIndexTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 标签展示 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-05-19
 */
@Mapper
public interface AppIndexTagMapper{

    int insert(@Param("appIndexTag")AppIndexTag appIndexTag);

    int update(@Param("appIndexTag")AppIndexTag appIndexTag);

    @Select("select * from app_index_tag where id = #{id} and is_deleted=0")
    AppIndexTag getById(@Param("id") BigInteger id);

    @Select("select * from app_index_tag where id = #{id}")
    AppIndexTag extractById(@Param("id")BigInteger id);

    @Update("update app_index_tag set is_deleted=1,update_time =#{updateTime} where id =#{id}")
    int delete(@Param("id")BigInteger id,@Param("updateTime")int updateTime);

    @Select("select * from app_index_tag where is_deleted=0")
    List<AppIndexTag> getAppIndexTagListForApp();

    List<AppIndexTag> extractAppIndexTagListForConsole(@Param("begin") int begin, @Param("size") Integer pageSize,@Param("showTagName") String showTagName);

    @Select("select count(*) from app_index_tag")
    Integer extractTotal();

    @Update("update app_index_tag set is_deleted=1, update_time = #{updateTime}")
    void deleteAll(int updateTime);

    @Update("update app_index_tag set is_deleted=0,update_time = #{updateTime} where id in (${newIdList})")
    void recover(@Param("newIdList") String newIdList,@Param("updateTime") int updateTime);
}
