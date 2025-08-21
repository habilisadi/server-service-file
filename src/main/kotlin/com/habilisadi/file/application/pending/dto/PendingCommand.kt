package com.habilisadi.file.application.pending.dto

import com.habilisadi.file.PendingRequest
import com.habilisadi.file.Status
import com.habilisadi.file.UpdatePendingRequest

class PendingCommand {
    data class Save(
        val userPk: String,
        val prevFileName: String,
        val prevFilePath: String,
        val destination: String
    ) {
        companion object {
            fun from(req: PendingRequest): Save {
                if (req.fileName.isNullOrBlank()) throw IllegalArgumentException("파일명이 없습니다.")
                if (req.destination.isNullOrBlank()) throw IllegalArgumentException("저장 경로가 없습니다.")
                if (req.userPk.isNullOrBlank()) throw IllegalArgumentException("유저 정보가 없습니다.")
                if (req.filePath.isNullOrBlank()) throw IllegalArgumentException("파일명이 없습니다.")

                return Save(
                    userPk = req.userPk,
                    prevFileName = req.fileName,
                    prevFilePath = req.filePath,
                    destination = req.destination
                )
            }
        }
    }

    data class Update(
        val id: String,
        val status: Status
    ) {
        companion object {
            fun from(req: UpdatePendingRequest): Update {
                return Update(
                    id = req.id,
                    status = req.status
                )
            }
        }
    }
}