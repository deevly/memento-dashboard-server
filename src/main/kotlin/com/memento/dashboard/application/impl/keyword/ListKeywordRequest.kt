package com.memento.dashboard.application.impl.keyword

data class ListKeywordRequest(
    val username: String,
    val cursor: KeywordCursor
)
