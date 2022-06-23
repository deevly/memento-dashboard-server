package com.memento.dashboard.interfaces.keyword

import com.google.protobuf.Timestamp
import com.memento.dashboard.application.KeywordService
import memento.Keyword
import memento.KeywordServiceGrpcKt
import memento.Types.KeywordResult
import mu.KLogging
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class KeywordController(
    private val keywordService: KeywordService
) : KeywordServiceGrpcKt.KeywordServiceCoroutineImplBase() {

    private val logger = KLogging().logger

    override suspend fun listKeywords(request: Keyword.ListKeywordsRequest): Keyword.ListKeywordsResponse {
        try {
            val results = keywordService.getListKeywords(
                request.username,
                request.cursor.type,
                request.cursor.date
            )
            return Keyword.ListKeywordsResponse.newBuilder().addAllKeywords(
                results.map { result ->
                    KeywordResult.newBuilder()
                        .setKeyword(result.keyword)
                        .setCount(result.count)
                        .setVisitTime(
                            Timestamp.newBuilder()
                                .setSeconds(result.seconds)
                                .setNanos(result.nanos)
                                .build()
                    ).build()
                }
            ).build()
        } catch (e: Exception) {
            logger.error { e.stackTrace }
            return Keyword.ListKeywordsResponse.newBuilder().build()
        }
    }

    override suspend fun searchKeywords(request: Keyword.SearchKeywordsRequest): Keyword.SearchKeywordsResponse {
        try {
            val results = keywordService.getSearchKeywords(
                request.username,
                request.regex
            )
            return Keyword.SearchKeywordsResponse.newBuilder().addAllKeywords(
                results.map { result ->
                    KeywordResult.newBuilder()
                        .setKeyword(result.keyword)
                        .setCount(result.count)
                        .setVisitTime(
                            Timestamp.newBuilder()
                                .setSeconds(result.seconds)
                                .setNanos(result.nanos)
                                .build()
                        ).build()
                }
            ).build()
        } catch (e: Exception) {
            logger.error { e.stackTrace }
            return Keyword.SearchKeywordsResponse.newBuilder().build()
        }
    }
}