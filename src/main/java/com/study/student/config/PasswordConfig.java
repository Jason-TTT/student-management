package com.study.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//这个类是“配置类”，Spring 启动时会来这里看有没有需要创建的 Bean。
public class PasswordConfig {
    @Bean
//   我创建一个 PasswordEncoder 对象，而且实际实现用 BCrypt，然后交给 Spring 容器管理
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
