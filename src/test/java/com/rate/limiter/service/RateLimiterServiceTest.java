package com.rate.limiter.service;

import com.rate.limiter.config.RateLimitProperties;
import com.rate.limiter.dto.RateLimitResult;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RateLimiterServiceTest {

    private StringRedisTemplate redisTemplate;
    private RateLimitProperties rateLimitProperties;
    private RateLimiterService rateLimiterService;
    private ValueOperations<String,String> valueOperations;

    @BeforeEach
    void setup() {
        redisTemplate = mock(StringRedisTemplate.class);
        valueOperations =mock(ValueOperations.class);
        rateLimitProperties= new RateLimitProperties();

        rateLimitProperties.setMaxRequests(5);
        rateLimitProperties.setWindowSeconds(60);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        rateLimiterService = new RateLimiterService(redisTemplate,rateLimitProperties);
    }

    @Test
    void shouldAllowRequestWithinLimit() {

        when(valueOperations.increment("rate_limit:user123"))
                .thenReturn(1L);

        RateLimitResult result =
                rateLimiterService.isAllowed("user123");

        assertTrue(result.isAllowed());
        assertEquals(4, result.getRemainingRequests());

        verify(redisTemplate)
                .expire("rate_limit:user123", Duration.ofSeconds(60));
    }

    @Test
    void shouldRejectRequestWhenLimitExceeded() {

        when(valueOperations.increment("rate_limit:user123"))
                .thenReturn(6L);

        RateLimitResult result =
                rateLimiterService.isAllowed("user123");

        assertFalse(result.isAllowed());
        assertEquals(0, result.getRemainingRequests());
    }

    @Test
    void shouldAllowRequestWhenExactlyAtLimit() {

        when(valueOperations.increment("rate_limit:user123"))
                .thenReturn(5L);

        RateLimitResult result =
                rateLimiterService.isAllowed("user123");

        assertTrue(result.isAllowed());
        assertEquals(0, result.getRemainingRequests());
    }

    @Test
    void shouldSetTtlForFirstRequest() {

        when(valueOperations.increment("rate_limit:user123"))
                .thenReturn(1L);

        rateLimiterService.isAllowed("user123");

        verify(redisTemplate)
                .expire("rate_limit:user123", Duration.ofSeconds(60));
    }

//    @Test
//    void shouldHandleNullRedisResponse() {
//
//        when(valueOperations.increment("rate_limit:user123"))
//                .thenReturn(null);
//
//        RateLimitResult result =
//                rateLimiterService.isAllowed("user123");
//
//        assertFalse(result.isAllowed());
//        assertEquals(0, result.getRemainingRequests());
//    }
}
