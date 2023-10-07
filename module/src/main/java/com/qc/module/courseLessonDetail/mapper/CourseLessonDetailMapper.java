package com.qc.module.courseLessonDetail.mapper;

import com.qc.module.courseLessonDetail.entity.CourseLessonDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

/**
 * <p>
 * 课程节课详情表 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-13
 */
@Mapper
public interface CourseLessonDetailMapper {

    int insert(@Param("courseLessonDetail") CourseLessonDetail courseLessonDetail);

    int update(@Param("courseLessonDetail") CourseLessonDetail courseLessonDetail);

    @Select("select * from course_lesson_detail where id = #{id} and is_deleted=0")
    CourseLessonDetail getById(@Param("id") BigInteger id);

    @Select("select * from course_lesson_detail where id=#{id}")
    CourseLessonDetail extractById(@Param("id")BigInteger id);

    @Update("update course_lesson_detail  set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);

    @Select("select * from course_lesson_detail where id=#{courseLessonDetailId} and is_marketable = 1 and is_deleted = 0")
    CourseLessonDetail getAvailableCourseLessonDetailById(BigInteger courseLessonDetailId);
}
