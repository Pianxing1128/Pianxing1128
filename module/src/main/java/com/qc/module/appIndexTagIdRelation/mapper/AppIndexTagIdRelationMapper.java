package com.qc.module.appIndexTagIdRelation.mapper;

import com.qc.module.appIndexTagIdRelation.entity.AppIndexTagIdRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 展示标签关系表 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-05-20
 */
@Mapper
public interface AppIndexTagIdRelationMapper {

    int insert(@Param("appIndexTagIdRelation")AppIndexTagIdRelation appIndexTagIdRelation);

    int update(@Param("appIndexTagIdRelation")AppIndexTagIdRelation appIndexTagIdRelation);

    @Select("select from app_index_tag_id_relation where is_deleted=0 and id =#{id}")
    AppIndexTagIdRelation getById(@Param("id")BigInteger id);

    @Select("select from app_index_tag_id_relation where id =#{id}")
    AppIndexTagIdRelation extractById(@Param("id")BigInteger id);

    @Update("update app_index_tag_id_relation set is_deleted=1,updateTime=#{updateTime} where id =#{id}")
    int delete(@Param("id")BigInteger id,@Param("updateTime")int updateTime);

    @Select("select tag_id from app_index_tag_id_relation where show_tag_id=#{showTagId}")
    List<BigInteger> getTagIdByShowTagId(Integer showTagId);
}
