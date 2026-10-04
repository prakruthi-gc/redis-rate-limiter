package com.rate.limiter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {

    private int maxRequests;
    private int windowSeconds;

}
