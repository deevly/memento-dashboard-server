package com.memento.dashboard.application.impl.url

data class ListUrlsByKeywordResponse(
    val urls: List<UrlModel>,
    val cursor: UrlCursor
)
