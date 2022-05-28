package com.memento.dashboard.interfaces.url

import com.memento.dashboard.application.UrlService
import memento.Url
import memento.UrlServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UrlController(
    private val urlService: UrlService
    ) : UrlServiceGrpcKt.UrlServiceCoroutineImplBase() {

    override suspend fun listUrls(request: Url.ListUrlsRequest): Url.ListUrlsResponse {
        return Url.ListUrlsResponse.newBuilder().build()
    }

    override suspend fun listUrlsByKeyword(request: Url.ListUrlsByKeywordRequest): Url.ListUrlsByKeywordResponse {
        return Url.ListUrlsByKeywordResponse.newBuilder().build()
    }
}