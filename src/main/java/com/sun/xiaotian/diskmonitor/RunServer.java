package com.sun.xiaotian.diskmonitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.sun.xiaotian.diskmonitor.mapper")
public class RunServer {

    public static void main(String[] args) {
        SpringApplication.run(RunServer.class, args);
    }

}
