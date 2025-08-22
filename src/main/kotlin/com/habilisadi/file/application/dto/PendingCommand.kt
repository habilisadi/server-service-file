package com.habilisadi.file.application.dto

import com.habilisadi.file.Destination
import com.habilisadi.file.SavePendingRequest
import com.habilisadi.file.Status
import com.habilisadi.file.UpdatePendingRequest

class PendingCommand {
    data class Save(
        val userPk: String,
        val prevFileName: String,
        val prevFilePath: String,
        val destination: Destination
    ) {
        companion object {
            fun from(req: SavePendingRequest): Save {
                if (req.fileName.isNullOrBlank()) throw IllegalArgumentException("파일명이 없습니다.")
                if (req.destination == Destination.UNRECOGNIZED) throw IllegalArgumentException("사용 목적이 없습니다.")
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
        val userPk: String,
        val status: Status,
        val destination: Destination
    ) {
        companion object {
            fun from(req: UpdatePendingRequest): Update {
                if (req.id.isNullOrBlank()) throw IllegalArgumentException("파일명이 없습니다.")
                if (req.userPk.isNullOrBlank()) throw IllegalArgumentException("유저 정보가 없습니다.")
                if (req.status == null) throw IllegalArgumentException("상태 정보가 없습니다.")
                if (req.destination == Destination.UNRECOGNIZED) throw IllegalArgumentException("사용 목적이 없습니다.")

                return Update(
                    id = req.id,
                    userPk = req.userPk,
                    status = req.status,
                    destination = req.destination
                )
            }
        }
    }
}