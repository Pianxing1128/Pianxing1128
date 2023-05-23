package com.qc.module.course.mapper;


import com.qc.module.course.entity.CourseTag;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CourseTagMapper {

    int insert(@Param("courseTag")CourseTag courseTag);

    int update(@Param("courseTag")CourseTag courseTag);

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

    List<CourseTag> extractCourseTagList(@Param("begin") int begin, @Param("size") Integer pageSize,@Param("tag")String tag);

    @Select("select count(*) from course_tag")
    Integer getTotal();

    @Select("select * from course_tag where id = #{id} and is_deleted=0")
    CourseTag getById(BigInteger id);

    @Select("select * from course_tag where id = #{id}")
    CourseTag extractById(BigInteger id);

    @Update("update course_tag set is_deleted=1, update_time = #{updateTime} where id=#{id}")
    int delete(BigInteger id, int updateTime);

    @Update("update course_tag set is_deleted=0, update_time = #{updateTime} where id=#{id}")
    int recover(BigInteger id, int updateTime);
}
