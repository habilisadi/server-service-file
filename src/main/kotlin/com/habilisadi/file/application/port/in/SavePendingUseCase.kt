package com.habilisadi.file.application.port.`in`

import com.habilisadi.file.SavePendingResponse
import com.habilisadi.file.application.dto.PendingCommand

interface SavePendingUseCase {
    fun save(command: PendingCommand.Save): SavePendingResponse
}