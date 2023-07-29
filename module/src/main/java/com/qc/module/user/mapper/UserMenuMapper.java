package com.qc.module.user.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMenuMapper {

    @Select("select menu_code from user_menu where id in (${menuIds}) and is_deleted=0")
    List<String> getPermissionsByUserId(String menuIds);
}
