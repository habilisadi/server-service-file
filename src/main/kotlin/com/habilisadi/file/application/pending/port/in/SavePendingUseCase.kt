package com.habilisadi.file.application.pending.port.`in`

import com.habilisadi.file.PendingResponse
import com.habilisadi.file.application.pending.dto.PendingCommand

interface SavePendingUseCase {
    fun save(command: PendingCommand.Save): PendingResponse
}