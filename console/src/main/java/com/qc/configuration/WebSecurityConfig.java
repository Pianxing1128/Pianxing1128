package com.qc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用预授权和后授权注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ConsoleAuthenticationSuccessHandler consoleAuthenticationSuccessHandler;

    @Resource
    private ConsoleAuthenticationFailHandler consoleAuthenticationFailHandler;
    protected void configure(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests()  //授权Http请求
//                  .anyRequest()
//                  .denyAll();
                    .mvcMatchers("/course/**")
                    .hasAnyAuthority("teacher:add")
                    .mvcMatchers("/course/**")
                    .hasAnyRole("ROLE_teacher")
                    .anyRequest()
                    .authenticated();
        //设置登录成功处理器
        http.formLogin().successHandler(consoleAuthenticationSuccessHandler).failureHandler(consoleAuthenticationFailHandler).permitAll();  //允许表单登陆

    }
}
