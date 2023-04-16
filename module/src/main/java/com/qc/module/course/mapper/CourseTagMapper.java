package com.qc.module.course.mapper;

import com.qc.module.course.entity.CourseTag;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CourseTagMapper {

    @Select("select id from course_tag where tag like CONCAT('%',#{tag},'%')")
    List<String> getIdsByTag(@Param("tag") String tag);

    @Select("select id from course_tag where tag = #{tag} and is_deleted=0")
    BigInteger getId(String tag);


    int insert(@Param("CourseTag") CourseTag courseTag);

    @Select("select tag from course_tag where id in (${tagIds})")
    List<String> getTag(String tagIds);

    @Delete("update course_tag set is_deleted = 1 where tag = #{tag}")
    int delete(String tag);
}
