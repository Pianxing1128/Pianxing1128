package com.qc.module.course.mapper;

import com.qc.module.course.entity.CourseTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CourseTagRelationMapper {

    @Select("select * from course_tag_relation where id =#{id}")
    CourseTagRelation getById(@Param("id")BigInteger id);

    @Select("select id from course_tag_relation where course_id=#{courseId} and tag_id = #{tagId} and is_deleted=0")
    BigInteger getId(@Param("id")BigInteger id,@Param("tagId")BigInteger tagId);

    int insert(@Param("courseTagRelation") CourseTagRelation courseTagRelation);

    @Select("select id from course_tag_relation where course_id=#{courseId} and tag_id in (${ss})")
    List<BigInteger> extractIdList(BigInteger courseId,String ss);

    @Select("select id from course_tag_relation where course_id=#{courseId} and tag_id = #{tagId}")
    BigInteger extractIdByCourseIdAndTagId(BigInteger courseId,BigInteger tagId);

    @Select("select tag_id from course_tag_relation where course_id = #{courseId} and is_deleted=0")
    List<BigInteger> getTagIds(BigInteger courseId);

    @Select("select id from course_tag_relation where course_id = #{courseId}")
    List<BigInteger> extractIds(BigInteger courseId);

    @Select("select course_id from course_tag_relation where tag_id in (${tagId}) and is_deleted=0")
    List<String> getCourseIds(String idsByTag);

    @Update("update course_tag_relation set is_deleted = 1 , update_time = #{updateTime} where id =#{id}")
    int delete(BigInteger id,Integer updateTime);

    @Update("update course_tag_relation set is_deleted = 1 , update_time = #{updateTime} where course_id =#{courseId}")
    int deleteByCourseId(@Param("courseId") BigInteger courseId,@Param("updateTime")Integer updateTime);

    @Update("update course_tag_relation set is_deleted = 1 , update_time = #{updateTime} where tag_id =#{tagId}")
    int deleteByTagId(@Param("tagId") BigInteger tagId,@Param("updateTime")int updateTime);

    @Update("update course_tag_relation set is_deleted = 0 , update_time = #{updateTime} where tag_id =#{tagId}")
    int recoverByTagId(BigInteger tagId, int updateTime);

    @Update("update course_tag_relation set is_deleted = 0 , update_time = #{updateTime} where id =#{id}")
    int recover(BigInteger id,Integer updateTime);

    @Select("select id from course_tag_relation where course_id =#{courseId}")
    List<BigInteger> extractIdByCourseId(BigInteger courseId);

    @Select("select id from course_tag_relation where tag_id = #{tagId}")
    List<BigInteger> extractIdByTagId(BigInteger tagId);

    List<CourseTagRelation> extractCourseTagRelationList(@Param("begin") int begin,@Param("size") Integer pageSize,@Param("courseId")BigInteger courseId,@Param("tagId") BigInteger tagId);

    @Select("select count(*) from course_tag_relation")
    Integer extractTotal();

    int update(@Param("courseTagRelation")CourseTagRelation courseTagRelation);

    @Select("select * from course_tag_relation where id =#{id}")
    CourseTagRelation extractById(BigInteger id);
}
