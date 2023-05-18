package com.qc.controller.user;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.user.UserInfoVo;
import com.qc.domain.user.UserLoginInfoVo;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import com.qc.utils.IpUtils;
import com.qc.utils.Response;
import com.qc.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/register")
    public Response userRegister(@VerifiedUser User loginUser,
                                 @RequestParam(name="userAccount")String userAccount,
                                 @RequestParam(name="userPassword") String userPassword,
                                 @RequestParam(name="checkPassword") String checkPassword,
                                 @RequestParam(name="birthday") Integer birthday,
                                 @RequestParam(name="email") String email,
                                 @RequestParam(required = false,name="avatar") String avatar,
                                 @RequestParam(required = false,name="gender") Integer gender,
                                 @RequestParam(required = false,name="nickName") String nickName,
                                 @RequestParam(required = false,name="userIntro") String userIntro
                                ) {

        if(!BaseUtils.isEmpty(loginUser)){
            return new Response(4004);
        }
        if(!BaseUtils.isEmpty(email)){
            email=email.trim();
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
                  id = userService.userRegister(userAccount,userPassword,checkPassword,avatar,gender,nickName,birthday,userIntro,email,IpUtils.getIpAddress(request));
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
        userInfo.setBirthday(BaseUtils.timeStamp2Date(user.getBirthday()));
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

        if(!BaseUtils.isEmpty(loginUser)){
            return new Response(4004);
        }

        boolean result = userService.login(userAccount, userPassword);
        if (!result) {
            return new Response(4004);
        }
        User user = userService.getUserAccount(userAccount);

        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        userService.refreshUserLoginContext(user.getId(), IpUtils.getIpAddress(request), BaseUtils.currentSeconds());

        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setUserAccount(user.getUserAccount());
        userInfo.setNickName(user.getNickName());
        userInfo.setEmail(user.getEmail());
        userInfo.setBirthday(BaseUtils.timeStamp2Date(user.getBirthday()));
        userInfo.setGender(user.getGender());
        userInfo.setUserIntro(user.getUserIntro());

        UserLoginInfoVo loginInfo = new UserLoginInfoVo();

        loginInfo.setSign(SignUtils.makeSign(user.getId()));
        loginInfo.setUserInfo(userInfo);

        return new Response(1001, loginInfo);
    }

    /**
     * 用户在app修改一些信息，修改成功后应该自动刷新userInfo页面
     */
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
