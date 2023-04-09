package com.qc.controller;

import com.qc.common.BaseResponse;
import com.qc.common.ResultUtils;
import com.qc.exception.GlobalExceptionHandler;
import com.qc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;


    @RequestMapping("/user/register")
    public BaseResponse<String> userRegister(@RequestParam(name="userAccount")String userAccount,
                                                 @RequestParam(name="userPassword") String userPassword,
                                                 @RequestParam(name="checkPassword") String checkPassword) {
        try{
            BigInteger newId = userService.userRegister(userAccount, userPassword, checkPassword);
            return ResultUtils.success(("新增用户的id为:"+newId));
        }catch (Exception e){
            return ResultUtils.error(e.getMessage());
        }
    }

    @RequestMapping("/user/login")
    public BaseResponse<String> userLogin(@RequestParam(name="userAccount")String userAccount,
                                             @RequestParam(name="userPassword") String userPassword,
                                          HttpServletRequest request) {

        try{
            String sign = userService.userLogin(userAccount, userPassword, request);
            return ResultUtils.success(("登录成功,返回的sign值为:"+sign));
        }catch (RuntimeException e){
            return null;
        }

    }
}
