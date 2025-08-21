package com.habilisadi.file.adapter.pending.`in`

import com.habilisadi.file.*
import com.habilisadi.file.application.pending.dto.PendingCommand
import com.habilisadi.file.application.pending.port.`in`.SavePendingUseCase
import io.grpc.stub.StreamObserver
import org.springframework.grpc.server.service.GrpcService

@GrpcService
class PendingController(
    private val savePendingUseCase: SavePendingUseCase
) : PendingServiceGrpc.PendingServiceImplBase() {
    override fun getPendingRequests(request: PendingRequest, responseObserver: StreamObserver<PendingResponse>) {
        val command = PendingCommand.Save.from(request)

        val response = savePendingUseCase.save(command)

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun updatePendingRequests(
        request: UpdatePendingRequest?,
        responseObserver: StreamObserver<UpdatePendingResponse>?
    ) {

    }
}