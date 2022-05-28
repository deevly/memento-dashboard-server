package com.memento.dashboard.application.impl.keyword

data class SearchKeywordResponse(
    val keywords: List<String>,
    val cursor: KeywordCursor
)
