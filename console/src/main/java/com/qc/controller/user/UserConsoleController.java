package com.qc.controller.user;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.user.UserVo;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.Response;
import jdk.nashorn.internal.runtime.UserAccessorProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qc
 */
@RestController
@Slf4j
public class UserConsoleController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public BaseListVo userList(@RequestParam(name = "pageNum")Integer pageNum){
        int pageSize = 3;
        BaseListVo result = new BaseListVo();
        List<User> users = userService.getUsersForConsole(pageNum, pageSize);
        List<UserVo> list = new ArrayList<>();

        for(User u:users){
            UserVo entry = new UserVo();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            entry.setId(u.getId());
            entry.setAvatar(u.getAvatar());
            entry.setGender(u.getGender());

            Long time0=Long.valueOf(u.getBirthday()+"000");
            String birthday = sdf.format(new Date(time0));
            entry.setBirthday(birthday);

            entry.setNickName(u.getNickName());
            entry.setEmail(u.getEmail());
            entry.setLastLoginIp(u.getLastLoginIp());
            entry.setRegisterIp(u.getRegisterIp());
            entry.setIsDeleted(u.getIsDeleted());


            Long time1 = Long.valueOf(u.getCreateTime()+"000");
            String createTime = sdf.format(new Date(time1));
            entry.setCreateTime(createTime);

            Long time2 = Long.valueOf(u.getUpdateTime()+"000");
            String updateTime = sdf.format(new Date(time2));
            entry.setUpdateTime(updateTime);

            list.add(entry);
        }
        result.setUserList(list);
        result.setPageSize(pageSize);
        result.setUserTotal(userService.getTotal());
        return result;
    }

    @RequestMapping("user/getById")
    public User userGetById(BigInteger id) {
        return userService.getById(id);
    }

    @RequestMapping("/user/edit")
    public Response userEdit(@VerifiedUser User loginUser,
                             @RequestParam(required = false)BigInteger id,
                             @RequestParam(required = false,name="userAccount")String userAccount,
                             @RequestParam(required = false,name="userPassword")String userPassword,
                             @RequestParam(required = false,name="avatar")String avatar,
                             @RequestParam(required = false,name="nickName")String nickName,
                             @RequestParam(required = false,name="gender")Integer gender,
                             @RequestParam(required = false,name="email")String email,
                             @RequestParam(required = false,name="userIntro")String userIntro,
                             @RequestParam(required = false,name="birthday")Integer birthday) {

        try {
            BigInteger result = userService.editUser(id, userAccount, userPassword, avatar, nickName, gender, email, userIntro, birthday);
            return new Response(1001,result);
        }catch (Exception e){
            return new Response(4004);
        }

    }
}


