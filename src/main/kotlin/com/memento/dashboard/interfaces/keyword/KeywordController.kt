package com.memento.dashboard.interfaces.keyword

import com.memento.dashboard.application.KeywordService
import memento.Keyword
import memento.KeywordServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class KeywordController(
    private val keywordService: KeywordService
) : KeywordServiceGrpcKt.KeywordServiceCoroutineImplBase() {

    override suspend fun listKeywords(request: Keyword.ListKeywordsRequest): Keyword.ListKeywordsResponse {
        return super.listKeywords(request)
    }

    override suspend fun searchKeywords(request: Keyword.SearchKeywordsRequest): Keyword.SearchKeywordsResponse {
        return super.searchKeywords(request)
    }
}