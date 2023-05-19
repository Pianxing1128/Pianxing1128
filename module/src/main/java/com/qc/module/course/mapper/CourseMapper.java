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

    List<Course> getCoursesByCourseNameAndNickNameAndTag(@Param("begin") Integer begin,
                                                   @Param("size") Integer pageSize,
                                                   String courseName,String idsByTeacherId,String courseIds);

    List<NewCourse> getCourseTeacherUserByRealNameAndNickName(@Param("begin") Integer begin,
                                                              @Param("size") Integer pageSize,
                                                              String realName, String nickName);

}
