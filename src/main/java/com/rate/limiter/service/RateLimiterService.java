package com.rate.limiter.service;

import com.rate.limiter.config.RateLimitProperties;
import com.rate.limiter.dto.RateLimitResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private  final StringRedisTemplate redisTemplate;
    private final RateLimitProperties properties;

    public RateLimiterService(StringRedisTemplate redisTemplate,RateLimitProperties properties)
    {
        this.redisTemplate=redisTemplate;
        this.properties=properties;
    }

    public RateLimitResult isAllowed(String clientId)
    {
        String key ="rate_limit:"+clientId;

        Long count = redisTemplate.opsForValue().increment(key);
        if(count!=null && count==1)
        {
            Duration dur = Duration.ofSeconds(properties.getWindowSeconds());
            redisTemplate.expire(key,dur);
        }

        long currentCount = count != null ? count : 0;

        boolean allowed = currentCount <= properties.getMaxRequests();

        long remainingRequests = Math.max(
                properties.getMaxRequests() - currentCount,
                0
        );
        RateLimitResult result = new RateLimitResult(allowed,remainingRequests);

        return  result;
    }
}
