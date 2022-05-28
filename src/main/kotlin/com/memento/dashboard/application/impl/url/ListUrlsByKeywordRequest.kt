package com.memento.dashboard.application.impl.url

import com.memento.dashboard.domain.url.SiteDomain

data class ListUrlsByKeywordRequest(
    val username: String,
    val keyword: String,
    val domain: SiteDomain,
    val cursor: UrlCursor
)
