package com.luyuze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.luyuze.mapper") // 扫描 mybatis 通用 mapper 所在的包
@ComponentScan(basePackages = {"com.luyuze", "org.n3r.idworker"}) // 扫描所有包及相关组件包
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
