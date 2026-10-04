# Redis Rate Limiter

A simple distributed rate limiter built using Java, Spring Boot, Redis, Docker, and Docker Compose.

## Overview

This project implements a fixed-window rate limiting mechanism using Redis.

Each client is allowed a maximum of **5 requests within a 60-second window**.

Redis `INCR` is used to atomically maintain the request count, while Redis TTL automatically expires the rate-limit key after the configured window.

## Tech Stack

- Java 21
- Spring Boot 4.1.1
- Spring Data Redis
- Redis 7
- Maven
- JUnit 5
- Mockito
- Docker
- Docker Compose
- Spring Boot Actuator

## Architecture

```text
                    Client
                       |
                       v
             +-------------------+
             |   Spring Boot API |
             |    Port: 8080     |
             +---------+---------+
                       |
                       v
             +-------------------+
             | RateLimiterService|
             +---------+---------+
                       |
                       v
             +-------------------+
             |      Redis        |
             |    Port: 6379     |
             +-------------------+

Docker Architecturegit status
+------------------------------------------------+
|                 Docker Compose                 |
|                                                |
|  +----------------------+                      |
|  |   Spring Boot App    |                      |
|  |      Port 8080       |                      |
|  +----------+-----------+                      |
|             |                                  |
|             | redis:6379                       |
|             v                                  |
|  +----------------------+                      |
|  |       Redis 7        |                      |
|  |      Port 6379       |                      |
|  +----------------------+                      |
|                                                |
+------------------------------------------------+