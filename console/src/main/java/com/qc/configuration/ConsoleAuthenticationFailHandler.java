package com.qc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qc.domain.HttpResult.HttpResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class ConsoleAuthenticationFailHandler implements AuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpResultVo httpResultVo = HttpResultVo.builder().
                code(0).
                msg("登录失败").
                build();

        String responseJson = objectMapper.writeValueAsString(httpResultVo);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        PrintWriter writer = response.getWriter();
        writer.println(responseJson);
        writer.flush();
    }
}
