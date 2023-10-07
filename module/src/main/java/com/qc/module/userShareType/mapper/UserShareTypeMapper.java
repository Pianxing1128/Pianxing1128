package com.qc.module.userShareType.mapper;

import com.qc.module.userShareType.entity.UserShareType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;

/**
 * <p>
 * 用户分享类型 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-15
 */
@Mapper
public interface UserShareTypeMapper {

    int insert(@Param("userShareType") UserShareType userShareType);

    int update(@Param("userShareType") UserShareType userShareType);

    @Select("select * from user_share_type where id = #{id} and is_deleted=0")
    UserShareType getById(@Param("id") BigInteger id);

    @Select("select * from user_share_type where id=#{id}")
    UserShareType extractById(@Param("id")BigInteger id);

    @Update("update user_share_type set is_deleted=1,update_time=#{updateTime} where id = #{id}")
    int delete(@Param("id") BigInteger id,@Param("updateTime") int updateTime);
}
