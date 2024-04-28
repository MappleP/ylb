package com.itszb.ylb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
@MapperScan("com.itszb.ylb.mapper")
public class YlbApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YlbApiApplication.class, args);

	}

}
