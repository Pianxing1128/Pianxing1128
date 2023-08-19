package com.qc.module.course.mapper;

import com.qc.module.course.entity.Course;
import com.qc.module.course.entity.NewCourse;
import org.apache.ibatis.annotations.*;
import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CourseMapper {

    int insert(@Param("course") Course course);

    int update(@Param("course") Course course);

    @Select("select * from course where id = #{id} and is_deleted=0")
    Course getById(@Param("id") BigInteger id);

    @Select("select * from course where id=#{id}")
    Course extractById(@Param("id")BigInteger id);

    @Update("update course set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);

    @Select("select count(*) from course")
    Integer getTotal();

    @Select("select * from course where is_delete = 0 limit #{begin}, {pageSize}")
    List<Course> getCoursesForApp(@Param("begin")Integer pageNum, @Param("size")Integer pageSize);

    List<Course> getCoursesByCourseNameAndNickNameAndShowTagId(@Param("begin") Integer begin, @Param("size") Integer pageSize,@Param("courseName") String courseName,
                                                               @Param("idsByTeacherId")String idsByTeacherId,@Param("courseIds")String courseIds,
                                                               @Param("orderedName")String orderedName, @Param("courseType")Integer courseType);

    List<NewCourse> getCourseTeacherUserByRealNameAndNickName(@Param("begin") Integer begin,
                                                              @Param("size") Integer pageSize,
                                                              String realName, String nickName);
    @Select("select course_type from course where id=#{courseId} and is_marketable = 1 and is_deleted =0")
    Integer getCourseTypeById(BigInteger courseId);

    @Select("select * from course where id=#{courseId} and is_marketable = 1 and is_deleted =0")
    Course getAvailableCourseById(BigInteger courseId);
}
