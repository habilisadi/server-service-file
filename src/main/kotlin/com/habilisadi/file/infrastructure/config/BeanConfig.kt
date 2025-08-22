package com.habilisadi.file.infrastructure.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
//            findAndRegisterModules()
            registerModule(JavaTimeModule())

            // 타임스탬프 대신 ISO 문자열로 직렬화
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

            // 추가 설정들
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
        }
    }
}