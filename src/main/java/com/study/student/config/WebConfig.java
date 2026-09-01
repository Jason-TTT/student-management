package com.study.student.config;

import com.study.student.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//Spring MVC 的配置接口 扩展卡槽来让MVC根据你的规则来控制controller
public class WebConfig implements WebMvcConfigurer {
    //拦截器注册表 / 拦截器注册中心
        public void addInterceptors(InterceptorRegistry registry) {
//            spring传来的时候自动new了 registry 注册一个拦截器表
//            把 LoginInterceptor 注册进 Spring MVC

            registry.addInterceptor(new LoginInterceptor()).addPathPatterns(
            "/student/**",
            "/auth/me",
            "/auth/logout",
            "/auth/change-password");
//           路径匹配规则
        }

}
