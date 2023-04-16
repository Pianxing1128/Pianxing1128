package com.qc.module.course.mapper;

import com.qc.module.course.entity.CourseTagRelation;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CourseTagRelationMapper {


    int insert(@Param("CourseTagRelation") CourseTagRelation courseTagRelation);

    @Select("select id from course_tag_relation where course_id = #{courseId} and tag_id = #{tagId} and is_deleted=0")
    BigInteger getRelationId(BigInteger courseId,BigInteger tagId);

    @Select("select tag_id from course_tag_relation where course_id = #{courseId} and is_deleted=0")
    List<String> getTagIds(BigInteger courseId);

    @Select("select course_id from course_tag_relation where tag_id in (${tagId}) and is_deleted=0")
    List<String> getCourseIds(String idsByTag);

    @Update("update course_tag_relation set is_deleted = 1 where tag_id =#{idByTag}")
    int delete(BigInteger idByTag);
}
