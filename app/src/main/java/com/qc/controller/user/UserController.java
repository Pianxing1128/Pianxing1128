package com.qc.controller.user;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.user.UserInfoVo;
import com.qc.domain.user.UserLoginInfoVo;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import com.qc.utils.Response;
import com.qc.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;


@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/user/register")
    public Response userRegister(@VerifiedUser User loginUser,
                                 @RequestParam(name="userAccount")String userAccount,
                                 @RequestParam(name="userPassword") String userPassword,
                                 @RequestParam(name="checkPassword") String checkPassword,
                                 @RequestParam(required = false,name="avatar") String avatar,
                                 @RequestParam(required = false,name="gender") Integer gender,
                                 @RequestParam(required = false,name="nickName") String nickName,
                                 @RequestParam(required = false,name="birthday") Integer birthday,
                                 @RequestParam(required = false,name="userIntro") String userIntro,
                                 @RequestParam(required = false,name="email") String email) {

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(4004);
        }
        //判断用户是否已经注册
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        User oldUser = userService.extractByUserAccount(userAccount);
        BigInteger id;
        if(!BaseUtils.isEmpty(oldUser)){
            if(oldUser.getIsDeleted() ==1 || oldUser.getIsBan() == 1){
                return new Response(1010);
            }else {
                return new Response(1011);
            }
        }else {
            try {
                //user = userService.userRegister(userAccount,userPassword,checkPassword,avatar,gender,nickName,birthday,userIntro,email,IpUtils.getIpAddress(request));
                id = userService.userRegister(userAccount,userPassword,checkPassword,avatar,gender,nickName,birthday,userIntro,email,"123.1.2.3");
            }catch (Exception e){
                return new Response(4004);
            }
        }

        UserInfoVo userInfo = new UserInfoVo();
        User user = userService.extractById(id);
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setUserAccount(user.getUserAccount());
        userInfo.setNickName(user.getNickName());
        userInfo.setEmail(user.getEmail());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setGender(user.getGender());
        userInfo.setUserIntro(user.getUserIntro());

        UserLoginInfoVo loginInfo = new UserLoginInfoVo();
        loginInfo.setSign(SignUtils.makeSign(user.getId()));
        loginInfo.setUserInfo(userInfo);

        return new Response(1001, loginInfo);

    }

    @RequestMapping("/user/login")
    public Response userLogin(@VerifiedUser User loginUser,
                              @RequestParam(name="userAccount")String userAccount,
                              @RequestParam(name="userPassword") String userPassword) {


        //如果用户登陆为空
        if(BaseUtils.isEmpty(loginUser)){
            return new Response(4004);
        }
        //合法登陆
        boolean result = userService.login(userAccount, userPassword);
        if (!result) {
            return new Response(4004);
        }
        User user = userService.getUserAccount(userAccount);
        //获取request更新登陆账户的信息
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        userService.refreshUserLoginContext(user.getId(), "123.1.2.3", BaseUtils.currentSeconds());
//        userService.refreshUserLoginContext(user.getId(), IpUtils.getIpAddress(request), BaseUtils.currentSeconds());

        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setUserAccount(user.getUserAccount());
        userInfo.setNickName(user.getNickName());
        userInfo.setEmail(user.getEmail());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setGender(user.getGender());
        userInfo.setUserIntro(user.getUserIntro());

        UserLoginInfoVo loginInfo = new UserLoginInfoVo();
        loginInfo.setSign(SignUtils.makeSign(user.getId()));
        loginInfo.setUserInfo(userInfo);

        return new Response(1001, loginInfo);
    }
}
