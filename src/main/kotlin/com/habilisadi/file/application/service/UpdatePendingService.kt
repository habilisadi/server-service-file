package com.habilisadi.file.application.service

import com.habilisadi.file.UpdatePendingResponse
import com.habilisadi.file.application.dto.PendingCommand
import com.habilisadi.file.application.port.`in`.UpdatePendingUseCase
import com.habilisadi.file.application.port.out.PendingRepository
import com.habilisadi.file.infrastructure.config.properties.RedisPendingKeyProperties
import org.springframework.stereotype.Service

@Service
class UpdatePendingService(
    private val redisPendingKeyProps: RedisPendingKeyProperties,
    private val pendingRepository: PendingRepository
) : UpdatePendingUseCase {
//    lateinit var pendingRepository: PendingRepository

    override fun update(command: PendingCommand.Update): UpdatePendingResponse {
        val key = "${redisPendingKeyProps.PENDING_PREFIX}:${command.destination}:${command.id}"
        val pendingEntity = pendingRepository.findByKey(key)
            ?: throw IllegalArgumentException("Pending not found")

        pendingEntity.status = command.status

        pendingRepository.saveValue(key, pendingEntity)

        return UpdatePendingResponse.newBuilder()
            .setId(pendingEntity.id)
            .setUserPk(pendingEntity.userPk)
            .setNextFileName(pendingEntity.nextFileName.value)
            .setNextFilePath(pendingEntity.nextFilePath.value)
            .build()
    }
}