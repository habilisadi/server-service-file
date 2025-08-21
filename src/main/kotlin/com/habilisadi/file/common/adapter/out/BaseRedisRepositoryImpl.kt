package com.habilisadi.file.common.adapter.out

import com.fasterxml.jackson.databind.ObjectMapper
import com.habilisadi.file.common.application.port.out.BaseRedisRepository
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

open class BaseRedisRepositoryImpl<T>(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objMapper: ObjectMapper
) : BaseRedisRepository<T> {
    override fun saveValue(key: String, value: T): Boolean {
        return this.saveValue(key, value, Duration.ofMinutes(10))
    }

    override fun saveValue(key: String, value: T, timeOut: Duration): Boolean {
        val json = objMapper.writeValueAsString(value)

        return try {
            redisTemplate.opsForValue().set(key, json, timeOut)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}