package com.qc.configuration;

import com.qc.filter.JwtCheckFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用预授权和后授权注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ConsoleAuthenticationSuccessHandler consoleAuthenticationSuccessHandler;

    @Resource
    private ConsoleAuthenticationFailHandler consoleAuthenticationFailHandler;

//    @Resource
//    private ConsoleLogoutSuccessHandler consoleLogoutSuccessHandler;
//
//    @Resource
//    private ConsoleAccessDenyHandler consoleAccessDenyHandler;

    @Resource
    private JwtCheckFilter jwtCheckFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //将JWT过滤器放在用户密码认证过滤器之前
        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().anyRequest().authenticated();
//        http.formLogin()
//                .loginPage("/login")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().csrf().disable().cors();
        http.formLogin().successHandler(consoleAuthenticationSuccessHandler).failureHandler(consoleAuthenticationFailHandler).permitAll();
//        http.logout().logoutSuccessHandler(consoleLogoutSuccessHandler);
//        http.exceptionHandling().accessDeniedHandler(consoleAccessDenyHandler);
        //不创建session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
