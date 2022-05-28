package com.memento.dashboard.application.impl.url

import com.memento.dashboard.domain.url.SiteDomain

data class UrlModel(
    val siteDomain: SiteDomain,
    val address: String,
    val keyword: String,
    val visitedTime: String
    )
