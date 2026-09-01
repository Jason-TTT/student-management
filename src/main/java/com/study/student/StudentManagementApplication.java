package com.study.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //这个类是整个 Spring Boot 项目的启动类  标识作用  从com。。开始向下扫描controller。。。
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
//这个 Java 程序启动之后，不再主要等待控制台输入，而是开始等待浏览器发来的 HTTP 请求

    }

}
