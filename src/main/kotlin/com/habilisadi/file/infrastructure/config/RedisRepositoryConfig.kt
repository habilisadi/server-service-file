package com.habilisadi.file.infrastructure.config

import org.springframework.context.annotation.Configuration

@Configuration
@EnabledRedisRepositories(
    basePackages = [
        "com.habilisadi.file.application.port.out",
        "com.habilisadi.file.common.application.port.out",
    ]
)
class RedisRepositoryConfig
