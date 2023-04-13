package com.qc.controller.user;

import com.alibaba.fastjson.JSON;
import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.user.UserVo;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import com.qc.utils.Response;
import com.qc.utils.SignUtils;
import com.qc.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class UserConsoleController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/login")
    public Response userLogin(@VerifiedUser User loginUser,
                              HttpSession httpSession,
                              @RequestParam(name="userAccount")String userAccount,
                              @RequestParam(name="userPassword") String userPassword,
                              @RequestParam(required = false,name = "remember") boolean remember) {


        //如果用户登陆为空
        if(BaseUtils.isEmpty(loginUser)){
            return new Response(4004);
        }

        boolean result;
        if(remember){
            result = userService.login(userAccount, userPassword);
        } else {
            result = userService.login(userAccount, userPassword, false, false, 0);
        }
        if (!result) {
            return new Response(1010);
        }
        User user = userService.getUserAccount(userAccount);
        //获取request更新登陆账户的信息
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        userService.refreshUserLoginContext(user.getId(), "123.1.2.3", BaseUtils.currentSeconds());
//        userService.refreshUserLoginContext(user.getId(), IpUtils.getIpAddress(request), BaseUtils.currentSeconds());

        UserVo userVo = new UserVo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        userVo.setId(user.getId());
        userVo.setAvatar(user.getAvatar());
        userVo.setUserAccount(user.getUserAccount());
        userVo.setNickName(user.getNickName());
        userVo.setEmail(user.getEmail());
        String birthday = sdf.format(new Date(Long.valueOf(user.getBirthday()+"000")));
        userVo.setBirthday(birthday);
        userVo.setGender(user.getGender());
        userVo.setUserIntro(user.getUserIntro());
        userVo.setRegisterIp(user.getRegisterIp());
        userVo.setLastLoginIp(user.getLastLoginIp());
        userVo.setIsBan(user.getIsBan());
        String creatTime = sdf.format(new Date(Long.valueOf(user.getCreateTime()+"000")));
        userVo.setCreateTime(creatTime);
        String updateTime = sdf.format(new Date(Long.valueOf(user.getUpdateTime()+"000")));
        userVo.setUpdateTime(updateTime);
        userVo.setIsDeleted(user.getIsDeleted());

        // 写session
        httpSession.setAttribute(SpringUtils.getProperty("application.session.key"), JSON.toJSONString(user));

        return new Response(1001, userVo);
    }
    @RequestMapping("/user/list")
    public Response userList(@VerifiedUser User loginUser,
                             HttpSession httpSession,
                             @RequestParam(name = "pageNum")Integer pageNum){

        if (!BaseUtils.isEmpty(loginUser)) {
            return new Response(4004);
        }
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

        // 写session
        httpSession.setAttribute(SpringUtils.getProperty("application.session.key"), JSON.toJSONString(users));

        return new Response(1001,result);
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


