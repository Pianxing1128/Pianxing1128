package com.qc.module.userPurchasedCourseRelation.mapper;

import com.qc.module.userPurchasedCourseRelation.entity.UserPurchasedCourseRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 用户购买课程表 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-13
 */
@Mapper
public interface UserPurchasedCourseRelationMapper {

    int insert(@Param("userPurchasedCourseRelation") UserPurchasedCourseRelation userPurchasedCourseRelation);

    int update(@Param("userPurchasedCourseRelation") UserPurchasedCourseRelation userPurchasedCourseRelation);

    @Select("select * from user_purchased_course_relation where id = #{id},is_deleted=0")
    UserPurchasedCourseRelation getById(@Param("id") BigInteger id);

    @Select("select * from user_purchased_course_relation where id=#{id}")
    UserPurchasedCourseRelation extractById(@Param("id")BigInteger id);

    @Update("update user_purchased_course_relation set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);

    @Select("select course_id from user_purchased_course_relation where user_id =#{userId} and is_deleted= 0")
    List<BigInteger> getCourseIdByUserId(BigInteger userId);
}
