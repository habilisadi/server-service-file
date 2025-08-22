package com.habilisadi.file.application.port.`in`

import com.habilisadi.file.UpdatePendingResponse
import com.habilisadi.file.application.dto.PendingCommand

interface UpdatePendingUseCase {
    fun update(command: PendingCommand.Update): UpdatePendingResponse
}