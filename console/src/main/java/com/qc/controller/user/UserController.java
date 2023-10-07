package com.qc.controller.user;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.user.UserVo;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/login")
    public Response userLogin(@VerifiedUser User loginUser,
                              HttpSession httpSession,
                              @RequestParam(name="userAccount")String userAccount,
                              @RequestParam(name="userPassword") String userPassword,
                              @RequestParam(required = false,name = "remember") boolean remember) {

//        //如果用户登陆不为空
//        if(BaseUtils.isEmpty(loginUser)){
//            return new Response(1002);
//        }

        boolean result;
        if(remember){
            result = userService.login(userAccount, userPassword);
        } else {
            result = userService.login(userAccount, userPassword, false, false, 0);
        }
        if (!result) {
            return new Response(1010);
        }
        User user = userService.getUser(userAccount);
        //获取request更新登陆账户的信息
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        userService.refreshUserLoginContext(user.getId(), IpUtils.getIpAddress(request), BaseUtils.currentSeconds());

        UserVo userVo = new UserVo();

        userVo.setId(user.getId());
        userVo.setAvatar(user.getAvatar());
        userVo.setUserAccount(user.getUserAccount());
        userVo.setNickName(user.getNickName());
        userVo.setEmail(user.getEmail());
        userVo.setBirthday(BaseUtils.timeStamp2Date(user.getBirthday()));
        userVo.setGender(user.getGender());
        userVo.setUserIntro(user.getUserIntro());
        userVo.setRegisterIp(user.getRegisterIp());
        userVo.setLastLoginIp(user.getLastLoginIp());
        userVo.setIsBan(user.getIsBan());
        userVo.setCreateTime(BaseUtils.timeStamp2Date(user.getCreateTime()));
        userVo.setUpdateTime(BaseUtils.timeStamp2Date(user.getUpdateTime()));
        userVo.setIsDeleted(user.getIsDeleted());

        // web登陆时候写session
//        httpSession.setAttribute(SpringUtils.getProperty("application.session.key"), JSON.toJSONString(user));

        Map<String, String> payload = new HashMap<>();
        payload.put("id", String.valueOf(user.getId()));
        payload.put("name", user.getUserAccount());
        payload.put("password", user.getUserPassword());

        // 生成token
        String token = JWTUtils.getToken(payload);
        userVo.setToken(token);
        return new Response(1001, userVo);
    }
    @RequestMapping("/user/list")
//    @CrossOrigin(origins = "http://192.168.110.131:3032", allowCredentials = "true")
    public Response userList(
                             @RequestParam(required = false,name = "pageNum")Integer inputPageNum){

//        if (BaseUtils.isEmpty(loginUser)) {
//            return new Response(1002);
//        }@VerifiedUser User loginUser,
        Integer pageNum = null;
        if(pageNum==null || pageNum<=0){
            pageNum = 1;
        }else {
            pageNum = inputPageNum;
        }
        Integer pageSize = Integer.valueOf(SpringUtils.getProperty("application.pagesize"));
        BaseListVo baseListVo = new BaseListVo();
        List<User> userList = userService.getUsersForConsole(pageNum, pageSize);
        List<UserVo> userVoList = new ArrayList<>();

        for(User u:userList){
            UserVo userVo = new UserVo();

            userVo.setId(u.getId());
            userVo.setAvatar(u.getAvatar());
            userVo.setGender(u.getGender());
            userVo.setBirthday(BaseUtils.timeStamp2Date(u.getBirthday()));
            userVo.setNickName(u.getNickName());
            userVo.setEmail(u.getEmail());
            userVo.setLastLoginIp(u.getLastLoginIp());
            userVo.setRegisterIp(u.getRegisterIp());
            userVo.setIsDeleted(u.getIsDeleted());
            userVo.setCreateTime(BaseUtils.timeStamp2Date(u.getCreateTime()));
            userVo.setUpdateTime(BaseUtils.timeStamp2Date(u.getUpdateTime()));

            userVoList.add(userVo);
        }
        baseListVo.setUserList(userVoList);
        baseListVo.setPageSize(pageSize);
        baseListVo.setUserTotal(userService.extractTotal());

        return new Response(1001,baseListVo);
    }

    @RequestMapping("user/getById")
    public Response userGetById(@VerifiedUser User loginUser,
                                @RequestParam(name = "id")BigInteger id) {
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        return new Response(1001, userService.getById(id));
    }

    @RequestMapping("/user/update")
    public Response userUpdate(@VerifiedUser User loginUser,
                               @RequestParam(name = "id")BigInteger id,
                               @RequestParam(required = false,name="userAccount")String userAccount,
                               @RequestParam(required = false,name="userPassword")String userPassword,
                               @RequestParam(required = false,name="avatar")String avatar,
                               @RequestParam(required = false,name="nickName")String nickName,
                               @RequestParam(required = false,name="gender")Integer gender,
                               @RequestParam(required = false,name="email")String email,
                               @RequestParam(required = false,name="userIntro")String userIntro,
                               @RequestParam(required = false,name="birthday")Integer birthday) {

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }

        try {
            BigInteger result = userService.editUser(id, userAccount, userPassword, avatar, nickName, gender, email, userIntro, birthday);
            return new Response(1001,result);
        }catch (Exception e){
            return new Response(4004);
        }

    }
}


