package com.rate.limiter.controller;

import com.rate.limiter.service.RedisTestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisTestController {

    private final RedisTestService redisTestService;
    public RedisTestController(RedisTestService redisTestService)
    {
        this.redisTestService=redisTestService;
    }

    @PostMapping
    public  String saveValues(@RequestParam String key,@RequestParam String value)
    {
        redisTestService.saveValue(key,value);
        return "value saved successfully";
    }

    @GetMapping
    public  String getValue(@RequestParam String key)
    {
        return  redisTestService.getValue(key);
    }
}
