package com.habilisadi.file.common.application.port.out

import java.time.Duration

interface BaseRedisRepository<T> {
    fun saveValue(key: String, value: T): Boolean
    fun saveValue(key: String, value: T, timeOut: Duration): Boolean
}