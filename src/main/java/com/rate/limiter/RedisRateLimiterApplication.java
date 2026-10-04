package com.rate.limiter;

import com.rate.limiter.config.RateLimitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RateLimitProperties.class)
public class RedisRateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisRateLimiterApplication.class, args);
	}

}
