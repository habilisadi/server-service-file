package com.habilisadi.file.application.pending.service

import com.habilisadi.file.PendingResponse
import com.habilisadi.file.application.pending.dto.PendingCommand
import com.habilisadi.file.application.pending.port.`in`.SavePendingUseCase
import com.habilisadi.file.application.pending.port.out.PendingRepository
import com.habilisadi.file.domain.pending.model.FileName
import com.habilisadi.file.domain.pending.model.FilePath
import com.habilisadi.file.domain.pending.model.PendingEntity
import org.springframework.stereotype.Service

@Service
class SavePendingService(
    private val pendingRepository: PendingRepository
) : SavePendingUseCase {
    private val PENDING_KEY_PREFIX = "pending:"

    override fun save(command: PendingCommand.Save): PendingResponse {

        val prevFilePath = FilePath(command.prevFilePath)
        val prevFileName = FileName(command.prevFileName)
        val nextFilePath = FilePath.of(command.userPk, command.destination)
        val nextFileName = FileName.of(prevFileName.toExt())

        val pendingEntity = PendingEntity(
            userPk = command.userPk,
            prevFileName = prevFileName,
            prevFilePath = prevFilePath,
            nextFileName = nextFileName,
            nextFilePath = nextFilePath,
            destination = command.destination,
        )

        val key = PENDING_KEY_PREFIX + pendingEntity.id


        val result = pendingRepository.saveValue(key, pendingEntity)

        if (!result) {
            throw IllegalArgumentException("저장에 실패했습니다.")
        }

        return PendingResponse.newBuilder()
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