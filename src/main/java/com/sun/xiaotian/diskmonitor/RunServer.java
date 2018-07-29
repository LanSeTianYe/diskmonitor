package com.sun.xiaotian.diskmonitor;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RunServer {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplicationBuilder()
                .sources(RunServer.class)
                .build();
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

}
