package com.capo.redisVersion2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisVersion2Application {
	public static void main(String[] args) {
		String[] customArgs = new String[] {"points","destinations"};
		SpringApplication.run(RedisVersion2Application.class, customArgs);
	}

}
