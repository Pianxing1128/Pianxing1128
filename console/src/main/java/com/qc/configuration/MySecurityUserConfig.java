package com.qc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MySecurityUserConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder().
                username("qc1128").
                password(passwordEncoder().encode("qc1128")).
                roles("teacher").
                authorities("teacher:add","teacher:delete","teacher:query").
                build();
        UserDetails user2 = User.builder().
                username("cc2023").
                password(passwordEncoder().encode("cc2023")).
                roles("teacher").
                build();
        UserDetails user3 = User.builder().
                username("cc1128").
                password(passwordEncoder().encode("cc1128")).
                authorities("student:add","student:delete").
                roles("student").  // roles和authorities 谁在后面谁起作用
                build();
        // InM 实现了上面的接口
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(user1);
        manager.createUser(user2);
        manager.createUser(user3);
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
