package com.jeremyli.jeremygenerator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jeremyli.jeremygenerator.mapper")
public class JeremyGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeremyGeneratorApplication.class, args);
    }

}
