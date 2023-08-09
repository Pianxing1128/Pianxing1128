package com.qc.module.membershipUser.mapper;

import com.qc.module.course.entity.Course;
import com.qc.module.membershipUser.entity.MembershipUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

/**
 * <p>
 * 用户会员表 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-09
 */
@Mapper
public interface MembershipUserMapper{

    int insert(@Param("membershipUser") MembershipUser membershipUser);

    int update(@Param("membershipUser") MembershipUser membershipUser);

    @Select("select * from membership_user where id = #{id} and is_deleted=0")
    Course getById(@Param("id") BigInteger id);

    @Select("select * from membership_user where id=#{id}")
    Course extractById(@Param("id")BigInteger id);

    @Update("update membership_user set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);

    @Select("select * from membership_user where user_id = #{userId} and is_deleted=0")
    MembershipUser getByUserId(BigInteger userId);
}
