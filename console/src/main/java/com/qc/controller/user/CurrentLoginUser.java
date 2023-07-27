package com.qc.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class CurrentLoginUser {

    @RequestMapping("/getUser1")
    public Authentication getLoginUser1(Authentication authentication){
        return authentication;
    }

    @RequestMapping("/getUser2")
    public Principal getLoginUser2(Principal principal){
        return principal;
    }

    @RequestMapping("/getUser3")
    public Principal getLoginUser3(){
        return SecurityContextHolder.getContext().getAuthentication();  //返回父接口

    }
}
