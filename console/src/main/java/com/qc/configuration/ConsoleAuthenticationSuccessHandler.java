package com.qc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qc.module.user.entity.HttpResult;
import com.qc.module.user.entity.MySecurityUser;
import com.qc.module.user.entity.User;
import com.qc.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ConsoleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //从认证信息中获取登录用户信息
        MySecurityUser securityUser= (MySecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        String strUserInfo = objectMapper.writeValueAsString(user);
        //获取用户的权限信息
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) securityUser.getAuthorities();
        //SimpleGrantedAuthority::getAuthority 调用SimpleGrantedAuthority 类的getAuthority方法
        // collect 收集
        List<String> authList=authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
        //生成jwt
        String jwtToken = jwtUtils.createJwt(strUserInfo, authList);
        HttpResult httpResult=HttpResult.builder()
                .code(1)
                .msg("jwt生成成功")
                .data(jwtToken)
                .build();
        //将jwt放到redis,设置过期时间和jwt的过期时间
        stringRedisTemplate.opsForValue().set("logintoken:"+jwtToken,objectMapper.writeValueAsString(authentication),2, TimeUnit.HOURS);
        printToken(request,response,httpResult);
    }

    private void printToken(HttpServletRequest request, HttpServletResponse response,HttpResult httpResult) throws IOException {
        String strResponse = objectMapper.writeValueAsString(httpResult);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(strResponse);
        writer.flush();
    }
}
