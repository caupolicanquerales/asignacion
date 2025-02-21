package com.capo.redisVersion2.config;

import java.io.IOException;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;

@Configuration
@ComponentScan
@EnableCaching
public class ConfigurationRedisson {
	
	@Value(value="${spring.redis.host}")
	private String host;
	
	@Value(value="${spring.redis.port}")
	private String port;
	
	@Bean
	RedissonClient reddison() throws IOException{
		Config config = new Config();
		config.useSingleServer().setAddress("redis://"+host+port);
		return Redisson.create(config);
	}
	
	
	@Bean
	public CacheManager cacheManager(RedissonClient redissonClient) {
		return new RedissonSpringCacheManager(redissonClient);
	}
}
