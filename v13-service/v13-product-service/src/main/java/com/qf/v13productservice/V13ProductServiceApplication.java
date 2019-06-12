package com.qf.v13productservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.qf.v13.mapper")
public class V13ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V13ProductServiceApplication.class, args);
    }

}
