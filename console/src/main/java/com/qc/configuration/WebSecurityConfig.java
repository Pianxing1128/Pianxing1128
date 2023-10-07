//package com.qc.configuration;
//
//import com.qc.filter.JwtCheckFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.annotation.Resource;
//import java.util.Arrays;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true) //启用预授权和后授权注解
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Resource
//    private ConsoleAuthenticationSuccessHandler consoleAuthenticationSuccessHandler;
//
//    @Resource
//    private ConsoleAuthenticationFailHandler consoleAuthenticationFailHandler;
//
//    @Resource
//    private ConsoleLogoutSuccessHandler consoleLogoutSuccessHandler;
//
//    @Resource
//    private JwtCheckFilter jwtCheckFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        //将JWT过滤器放在用户密码认证过滤器之前
//        http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);
//        http.cors() // 启用 CORS 支持
//                .and()
//                .csrf().disable() // 禁用 CSRF 保护
//                .authorizeRequests()
//                .antMatchers("/public/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//        http.formLogin().successHandler(consoleAuthenticationSuccessHandler).failureHandler(consoleAuthenticationFailHandler).permitAll();
//        http.logout().logoutSuccessHandler(consoleLogoutSuccessHandler);
////        http.csrf().disable(); //禁用跨域请求保护
////        http.exceptionHandling().accessDeniedHandler(consoleAccessDenyHandler);
//        //不创建session
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://192.168.110.101:3032"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
