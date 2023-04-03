package com.qc.mapper;

import com.qc.entity.Course;
import com.qc.entity.NewCourse;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CourseMapper {

    int insert(@Param("course") Course course);

    int update(@Param("course") Course course);

    @Select("select * from course where id = #{id} and is_deleted=0")
    Course getById(@Param("id") BigInteger id);

    @Select("select from course where id=#{id}")
    Course extractById(@Param("id")BigInteger id);

    @Update("update course set is_deleted=1 and update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") Integer updateTime);

    @Select("select count(*) from course")
    Integer getTotal();

    @Select("select * from course where is_delete = 0 limit #{begin}, {pageSize}")
    List<Course> getCoursesForApp(@Param("begin")Integer pageNum, @Param("size")Integer pageSize);

    @Select("select * from course limit #{begin}, {pageSize}")
    List<Course> getCoursesForConsole(@Param("begin")Integer pageNum, @Param("size")Integer pageSize);

    List<Course> getCoursesByCourseNameAndNickName(@Param("begin") Integer begin, @Param("size") Integer pageSize,
                                                   String courseName,String idsByTeacherId);

    List<NewCourse> getCourseTeacherUserByRealNameAndNickName(@Param("begin") Integer begin, @Param("size") Integer pageSize, String realName, String nickName);

}
