package com.habilisadi.file.infrastructure.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.habilisadi.file.application.pending.port.out.PendingRepository
import com.habilisadi.file.common.adapter.out.BaseRedisRepositoryImpl
import com.habilisadi.file.domain.pending.model.PendingEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig {

    @Bean
    fun pendingRepository(
        redisTemplate: RedisTemplate<String, String>,
        objMapper: ObjectMapper
    ): PendingRepository {
        return object : BaseRedisRepositoryImpl<PendingEntity>(redisTemplate, objMapper), PendingRepository {}
    }
}