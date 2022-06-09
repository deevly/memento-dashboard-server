package com.memento.dashboard.application.impl.url

data class UrlModel(
    val siteDomain: String,
    val address: String,
    val keyword: String,
    val visitedTimeSeconds: Long,
    val visitedTimeNanos: Int,
    val cursor: String
)
