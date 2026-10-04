package com.rate.limiter.controller;

import com.rate.limiter.dto.RateLimitResponse;
import com.rate.limiter.dto.RateLimitResult;
import com.rate.limiter.service.RateLimiterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate-limiter")
public class RateLimiterController {
    private final RateLimiterService rateLimiterService;
    public RateLimiterController(RateLimiterService rateLimiterService)
    {
        this.rateLimiterService=rateLimiterService;
    }

    @GetMapping
    public ResponseEntity<RateLimitResponse> isAllowed(@RequestParam String clientID)
    {
        RateLimitResult result= rateLimiterService.isAllowed(clientID);
        RateLimitResponse response= new RateLimitResponse(clientID,result.isAllowed(),result.getRemainingRequests(),result.isAllowed()?"Request Allowed":"Rate limit exceeded");
        if(!result.isAllowed())
        {
            return  ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
