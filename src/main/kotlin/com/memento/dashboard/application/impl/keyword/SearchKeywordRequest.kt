package com.memento.dashboard.application.impl.keyword

data class SearchKeywordRequest(
    val regex: String,
    val username: String,
    val cursor: String
)
