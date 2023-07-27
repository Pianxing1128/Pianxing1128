package com.qc.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")

    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
            ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),
                    "/druid/*");

        // 这些参数可以在 com.alibaba.druid.support.http.StatViewServlet
        // 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到
        Map<String, String> initParams = new HashMap<>(1000);
        //后台管理界面的登录账号
        initParams.put("loginUsername", "qc");
        //后台管理界面的登录密码
        initParams.put("loginPassword", "123");

        //后台允许谁可以访问
        initParams.put("allow", "localhost");//表示只有本机可以访问
        //initParams.put("allow", "")：为空或者为null时，表示允许所有访问
        //initParams.put("allow", "");
        //deny：Druid 后台拒绝谁访问
        //initParams.put("tianjiao", "192.168.1.20");表示禁止此ip访问

        //设置初始化参数
        bean.setInitParameters(initParams);
        return bean;
    }

    public FilterRegistrationBean<WebStatFilter> webStaticFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>(1000);
//        这些东西不进行统计
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        return bean;
    }
}
