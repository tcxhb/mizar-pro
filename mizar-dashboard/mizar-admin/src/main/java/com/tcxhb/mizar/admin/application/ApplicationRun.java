package com.tcxhb.mizar.admin.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {
        "com.tcxhb.mizar.dao.mapper",
})
@ComponentScan(basePackages = {
        "com.tcxhb.mizar.core.*",
        "com.tcxhb.mizar.admin.*",
        "com.tcxhb.mizar.dao.*",
})
public class ApplicationRun {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }
}
