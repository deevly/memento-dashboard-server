package com.memento.dashboard.application.impl.url

import com.memento.dashboard.domain.url.Url
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

data class UrlModel(
    val siteDomain: String,
    val address: String,
    val keyword: String,
    val visitedTimeSeconds: Long,
    val visitedTimeNanos: Int,
    val cursor: String
    ) {

    companion object {
        fun create(url: Url) : UrlModel {
            val visitedTime = getDateFromString(url.visitedTime)
            return UrlModel(
                url.siteDomain.name,
                url.url,
                url.keyword,
                (visitedTime / 1000),
                (visitedTime % 1000 * 1000000).toInt(),
                url.visitedTime
            )
        }

        private fun getDateFromString(visitedTime: String): Long {
            val datetimeformat: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val dateTime: DateTime = datetimeformat.parseDateTime(visitedTime)
            return dateTime.millis
        }
    }
}