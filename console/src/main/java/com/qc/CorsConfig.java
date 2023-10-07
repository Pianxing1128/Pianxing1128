package com.qc;

import com.qc.filter.JwtCheckFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);  //cookie允许

        // 允许访问的客户端域名
        List<String> allowedOriginPatterns = new ArrayList<>();
        // for web dev

        allowedOriginPatterns.add("http://192.168.110.131:3032");
        allowedOriginPatterns.add("http://192.168.110.101:3032");
        allowedOriginPatterns.add("http://localhost:8080");
        allowedOriginPatterns.add("*");
        corsConfiguration.setAllowedOriginPatterns(allowedOriginPatterns);
//        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
        return new CorsFilter(source);
    }

    @Configuration
    public class InterceptConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //添加拦截器
            registry.addInterceptor(new JwtCheckFilter())
                    //拦截的路径 需要进行token验证的路径
                    .addPathPatterns("/course/list")
                    //放行的路径
                    .excludePathPatterns("/user/login");
        }
    }
}


