package com.qc.module.course.mapper;


import com.qc.module.course.entity.CourseTag;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Primary;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CourseTagMapper {

    int insert(@Param("courseTag")CourseTag courseTag);

    @Select("select id from course_tag where tag like CONCAT('%',#{tag},'%')")
    List<BigInteger> getIdsByTag(@Param("tag") String tag);

    @Select("select id from course_tag where tag = #{tag} and is_deleted=0")
    BigInteger getId(String tag);

    @Select("select id from course_tag where tag=#{tag}")
    BigInteger extractIdByTag(@Param("tag") String tag);

    @Select("select tag from course_tag where id in (${tagIds}) and is_deleted=0")
    List<String> getTags(@Param("tagIds") String tagIds);

    @Delete("update course_tag set is_deleted = 1 and update_time = #{updateTime} where tag = #{tag}")
    int deleteByTag(String tag,int updateTime);

    @Select("select * from course_tag limit #{begin},#{size}")
    List<CourseTag> getTagList(@Param("begin") Integer begin, @Param("size") Integer pageSize);

    @Select("select count(*) from course_tag")
    Integer getTotal();
}
