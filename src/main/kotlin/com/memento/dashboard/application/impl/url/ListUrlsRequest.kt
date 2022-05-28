package com.memento.dashboard.application.impl.url

import com.memento.dashboard.domain.url.SiteDomain

data class ListUrlsRequest(
    val username: String,
    val domain: SiteDomain,
    val cursor: UrlCursor
)
