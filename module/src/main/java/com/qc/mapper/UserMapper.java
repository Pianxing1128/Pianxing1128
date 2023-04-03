package com.qc.mapper;

import com.qc.entity.Teacher;
import com.qc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@Mapper
public interface UserMapper{

    int insert(@Param("user")User user);

    int update(@Param("user")User user);

    @Select("select * from user where is_deleted=0 and id=#{id}")
    User getById(@Param("id") BigInteger id);

    @Select("select * from user where id=#{id}")
    User extractById(@Param("id")BigInteger id);

    @Update("update user set is_deleted=1 and update_time =#{updateTime} where id=#{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") Integer updateTime);

    @Select("select count(*) from user")
    Integer getTotal();

    @Select("select * from user where is_deleted=0 limit #{begin},#{pageSize}")
    List<User> getUsersForApp(@Param("begin") Integer begin,@Param("pageSize") Integer pageSize);

    @Select("select * from user limit #{begin},#{pageSize}")
    List<User> getUsersForConsole(@Param("begin") Integer begin,@Param("pageSize") Integer pageSize);

    @Select("select id from user where nick_name like concat('%',#{nickName},'%') and is_deleted=0")
    List<BigInteger> getUserIdsByNickname(String nickName);

    List<LinkedHashMap<String, Object>> getUserByHashMap(String nickName);

    List<String> getUserIdsByNickName(String nickName);

    List<User> getUsersByNickName(String nickName);

    List<User> getByIds(String userIdsFromTeacher);
}