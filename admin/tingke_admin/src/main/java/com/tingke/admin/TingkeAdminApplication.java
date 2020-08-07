package com.tingke.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.tingke.admin.mapper")
public class TingkeAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TingkeAdminApplication.class, args);
    }

}



