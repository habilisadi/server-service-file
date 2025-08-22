package com.habilisadi.file.adapter.`in`

import com.habilisadi.file.*
import com.habilisadi.file.application.dto.PendingCommand
import com.habilisadi.file.application.port.`in`.SavePendingUseCase
import com.habilisadi.file.application.port.`in`.UpdatePendingUseCase
import io.grpc.stub.StreamObserver
import org.springframework.grpc.server.service.GrpcService

@GrpcService
class PendingController(
    private val savePendingUseCase: SavePendingUseCase,
    private val updatePendingUseCase: UpdatePendingUseCase
) : PendingServiceGrpc.PendingServiceImplBase() {
    override fun savePendingRequests(
        request: SavePendingRequest,
        responseObserver: StreamObserver<SavePendingResponse>
    ) {
        val command = PendingCommand.Save.from(request)

        val response = savePendingUseCase.save(command)

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun updatePendingRequests(
        request: UpdatePendingRequest,
        responseObserver: StreamObserver<UpdatePendingResponse>
    ) {
        val command = PendingCommand.Update.from(request)

        val response = updatePendingUseCase.update(command)

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}