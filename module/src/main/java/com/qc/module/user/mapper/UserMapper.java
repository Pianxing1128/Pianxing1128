package com.qc.module.user.mapper;

import com.qc.module.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;


@Mapper
public interface UserMapper{

    int insert(@Param("user")User user);

    int update(@Param("user")User user);

    @Select("select * from user where id=#{id} and is_deleted=0")
    User getById(@Param("id") BigInteger id);

    @Select("select * from user where id=#{id}")
    User extractById(@Param("id")BigInteger id);

    @Select("select * from user where user_account=#{userAccount}")
    User extractByAccount(@Param("userAccount") String userAccount);

    @Select("select * from user where user_account=#{userAccount} and user_password = #{encryptPassword}")
    User extractByAccountAndEncryptPassword(@Param("userAccount")String userAccount,@Param("encryptPassword")String encryptPassword);

    @Update("update user set is_deleted=1 and update_time =#{updateTime} where id=#{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") Integer updateTime);

    @Select("select count(*) from user")
    Integer extractTotal();

    @Select("select * from user where is_deleted=0 limit #{begin},#{size}")
    List<User> getUsersForApp(@Param("begin") Integer begin,@Param("size") Integer pageSize);

    @Select("select * from user limit #{begin},#{size}")
    List<User> getUsersForConsole(@Param("begin") Integer begin,@Param("size") Integer size);

    @Select("select id from user where nick_name like concat('%',#{nickName},'%') and is_deleted=0")
    List<BigInteger> getUserIdsByNickname(String nickName);

    List<LinkedHashMap<String, Object>> getUserByHashMap(String nickName);

    List<String> getUserIdsByNickName(String nickName);

    List<User> getUsersByNickName(String nickName);

    List<User> getByIds(String userIdsFromTeacher);

    @Select("select * from user where user_account =#{userName} and is_deleted =0")
    User getUser(String userName);
    @Select("select * from user where email = #{email}")
    User extractByEmail(String email);
}