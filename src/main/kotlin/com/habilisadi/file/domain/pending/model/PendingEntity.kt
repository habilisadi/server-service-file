package com.habilisadi.file.domain.pending.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.f4b6a3.ulid.UlidCreator
import com.habilisadi.file.Status
import org.springframework.data.domain.AbstractAggregateRoot
import java.time.Instant

data class PendingEntity(
    var id: String = UlidCreator.getUlid().toString(),
    var userPk: String,
    var prevFileName: FileName,
    var prevFilePath: FilePath,
    var nextFileName: FileName,
    var nextFilePath: FilePath,
    var destination: String,
    var status: Status = Status.PENDING,
    var createdAt: Instant = Instant.now()
) : AbstractAggregateRoot<PendingEntity>() {

    companion object {
        fun fromJson(objMapper: ObjectMapper, json: String): PendingEntity {
            return objMapper.readValue(json, PendingEntity::class.java)
        }
    }

    fun toJson(objMapper: ObjectMapper): String {
        return objMapper.writeValueAsString(this)
    }
}
