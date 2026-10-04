package com.rate.limiter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RateLimitResponse {

    private String clientId;
    private boolean allowed;
    private long remainingRequest;
    private String message;
}
