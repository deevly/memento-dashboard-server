package com.memento.dashboard.interfaces.url

import com.google.protobuf.Timestamp
import com.memento.dashboard.application.UrlService
import com.memento.dashboard.domain.url.SiteDomain
import memento.Types
import memento.Types.URL
import memento.Types.URLCursor
import memento.Url
import memento.UrlServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService


@GrpcService
class UrlController(
    private val urlService: UrlService
    ) : UrlServiceGrpcKt.UrlServiceCoroutineImplBase() {

    override suspend fun listUrls(request: Url.ListUrlsRequest): Url.ListUrlsResponse {
        val results = urlService.getListUrls(
            request.username,
            SiteDomain.valueOf(request.domain.name),
            request.cursor.type,
            request.cursor.value
        )
        return Url.ListUrlsResponse.newBuilder()
            .addAllUrls(results.map { result ->
                URL.newBuilder()
                    .setAddress(result.address)
                    .setDomain(Types.SiteDomain.valueOf(result.siteDomain))
                    .setVisitTime(
                        Timestamp.newBuilder()
                            .setSeconds(result.visitedTimeSeconds)
                            .setNanos((result.visitedTimeNanos))
                            .build()
                    )
                    .setKeyword(result.keyword)
                    .build()
            })
            .setCursor(
                URLCursor.newBuilder()
                    .setType(request.cursor.type)
                    .setValue(results[results.size - 1].cursor)
                    .build()
            )
            .build()
    }

    override suspend fun listUrlsByKeyword(request: Url.ListUrlsByKeywordRequest): Url.ListUrlsByKeywordResponse {
        return Url.ListUrlsByKeywordResponse.newBuilder().build()
    }
}