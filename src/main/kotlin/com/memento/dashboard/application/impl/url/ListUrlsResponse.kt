package com.memento.dashboard.application.impl.url

data class ListUrlsResponse(
    val urls: List<UrlModel>,
    val cursor: UrlCursor
)