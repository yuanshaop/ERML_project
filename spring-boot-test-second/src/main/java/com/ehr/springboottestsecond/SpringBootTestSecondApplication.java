package com.ehr.springboottestsecond;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.ehr.springboottestsecond.model.dao")
@EnableCaching
public class SpringBootTestSecondApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestSecondApplication.class, args);
    }

}
