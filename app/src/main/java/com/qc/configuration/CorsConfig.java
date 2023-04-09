package com.qc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //表示允许所有的域都可以请求
                .allowedOrigins("local","http")
                //表示在3600秒内不需要再发送预校验请求
                .maxAge(3600)
                //是否发送Cookie信息
                .allowCredentials(true)
                //表示允许跨域请求的方法
                .allowedMethods("GET","POST", "PUT", "DELETE")
                //表示允许跨域请求包含content-type
                .allowedHeaders("*")
                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                .exposedHeaders("*");
    }
}
