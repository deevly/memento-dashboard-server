package com.memento.dashboard.application.impl.keyword

data class ListKeywordResponse(
    val keywords: List<String>,
    val cursor: KeywordCursor
)
