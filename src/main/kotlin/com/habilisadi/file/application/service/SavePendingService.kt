package com.habilisadi.file.application.service

import com.habilisadi.file.SavePendingResponse
import com.habilisadi.file.application.dto.PendingCommand
import com.habilisadi.file.application.port.`in`.SavePendingUseCase
import com.habilisadi.file.application.port.out.PendingRepository
import com.habilisadi.file.domain.model.FileName
import com.habilisadi.file.domain.model.FilePath
import com.habilisadi.file.domain.model.PendingEntity
import com.habilisadi.file.infrastructure.config.properties.RedisPendingKeyProperties
import org.springframework.stereotype.Service

@Service
class SavePendingService(
    private val redisPendingKeyProps: RedisPendingKeyProperties,
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    private val pendingRepository: PendingRepository
) : SavePendingUseCase {
    
    override fun save(command: PendingCommand.Save): SavePendingResponse {

        val prevFilePath = FilePath(command.prevFilePath)
        val prevFileName = FileName(command.prevFileName)
        val nextFilePath = FilePath.of(command.userPk, command.destination.name)
        val nextFileName = FileName.of(prevFileName.toExt())

        val pendingEntity = PendingEntity(
            userPk = command.userPk,
            prevFileName = prevFileName,
            prevFilePath = prevFilePath,
            nextFileName = nextFileName,
            nextFilePath = nextFilePath,
            destination = command.destination.name,
        )

        val key = "${redisPendingKeyProps.PENDING_PREFIX}:${command.destination.name}:${pendingEntity.id}"

        val result = pendingRepository.saveValue(key, pendingEntity)

        if (!result) {
            throw IllegalArgumentException("저장에 실패했습니다.")
        }

        return SavePendingResponse.newBuilder()
            .setId(pendingEntity.id)
            .setPrevFileName(pendingEntity.prevFileName.value)
            .setPrevFilePath(pendingEntity.prevFilePath.value)
            .setNextFileName(pendingEntity.nextFileName.value)
            .setNextFilePath(pendingEntity.nextFilePath.value)
            .setStatus(pendingEntity.status)
            .setCreatedAt(pendingEntity.createdAt.toString())
            .build()
    }
}