package com.rate.limiter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RateLimitResult {
    private boolean allowed;
    private long remainingRequests;

}

