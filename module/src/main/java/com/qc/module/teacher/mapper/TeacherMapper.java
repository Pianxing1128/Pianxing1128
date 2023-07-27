package com.qc.module.teacher.mapper;

import com.qc.module.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigInteger;
import java.util.List;

@Mapper
public interface TeacherMapper{

    int insert(@Param("teacher")Teacher teacher);

    int update(@Param("teacher")Teacher teacher);

    @Select("select * from teacher where is_deleted=0 and id=#{id}")
    Teacher getById(@Param("id") BigInteger id);

    @Select("select * from teacher where id=#{id}")
    Teacher extractById(@Param("id")BigInteger id);

    @Update("update teacher set is_deleted=1,update_time =#{updateTime} where id=#{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime")int updateTime);

    @Select("select count(*) from teacher")
    Integer getTotal();

    @Select("select * from teacher where is_deleted=0 limit #{begin},#{pageSize}")
    List<Teacher> getTeachersForApp(@Param("begin") Integer begin,@Param("pageSize") Integer pageSize);

    List<Teacher> extractTeacherList(@Param("begin") int begin,@Param("size") Integer pageSize,@Param("realName")String realName);

    List<BigInteger> getTeacherIdsByUserId(String UserIdsForTeacher);

    List<Teacher> getTeachersByNickName(@Param("begin")Integer begin, @Param("pageSize")Integer pagesize,String idsByUserId);

    List<Teacher> getByIds(String teacherIdsFromCourse);
}




