package com.qc.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qc.domain.HttpResult.HttpResultVo;
import com.qc.module.user.entity.User;
import com.qc.utils.BaseUtils;
import com.qc.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtCheckFilter extends OncePerRequestFilter {

    @Resource
    private JWTUtils jwtUtils;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //如果是登陆请求url，直接放行
        if(requestURI.equals("/login")){
            doFilter(request,response,filterChain);
            return;
        }
        String strAuthorization = request.getHeader("Authorization");
        if(BaseUtils.isEmpty(strAuthorization)){
            HttpResultVo httpResultVo = HttpResultVo.builder()
                    .code(0)
                    .msg("Authorization is null!")
                    .data(null)
                    .build();
            printToken(request,response,httpResultVo);
            return;
        }

        String jwtToken = strAuthorization.replace("bearer ","");
        if(BaseUtils.isEmpty(jwtToken)){
            HttpResultVo httpResultVo = HttpResultVo.builder()
                    .code(0)
                    .msg("JWT is null!")
                    .data(null)
                    .build();
            printToken(request,response,httpResultVo);
            return;
        }

        //校验jwt
        Boolean jwtVerification = jwtUtils.verifyToken(jwtToken);
        if(!jwtVerification){
            HttpResultVo httpResultVo = HttpResultVo.builder()
                    .code(0)
                    .msg("JWT is illegal!")
                    .data(null)
                    .build();
            printToken(request,response,httpResultVo);
            return;
        }

        //从jwt里获取用户信息和权限信息
        String userInfo = jwtUtils.getUserInfoFromToken(jwtToken);
        List<String> userAuthList =  jwtUtils.getUserAuthFromToken(jwtToken);
        //反序列化成User对象
        User user = objectMapper.readValue(userInfo, User.class);
        List<SimpleGrantedAuthority> authorityList = userAuthList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        //用户名密码认证token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,null,authorityList);
        //把token放到安全上下文：securityContext
        SecurityContextHolder.getContext().setAuthentication(token);
        doFilter(request,response,filterChain);
    }

    private void printToken(HttpServletRequest request, HttpServletResponse response, HttpResultVo httpResultVo) throws IOException {
        String strResponse = objectMapper.writeValueAsString(httpResultVo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(strResponse);
        writer.flush();
    }
}
