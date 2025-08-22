package com.habilisadi.file.infrastructure.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "redis.pending")
@Component
data class RedisPendingKeyProperties(
    val PENDING_PREFIX: String = "pending"
)