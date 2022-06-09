package com.memento.dashboard.application.impl.url

import com.memento.dashboard.application.UrlService
import com.memento.dashboard.domain.url.SiteDomain
import com.memento.dashboard.domain.url.UrlDomainService
import memento.Types.URLCursor.SortType
import mu.KLogging
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.stereotype.Service

@Service
class UrlServiceImpl (
    private val urlDomainService: UrlDomainService,
        ) : UrlService {

    private val logger = KLogging().logger

    override fun getListUrls(username: String, domain: SiteDomain, sortType: SortType, cursorValue: String) : List<UrlModel> {
        if (sortType == SortType.UNRECOGNIZED) {
            throw Exception("unrecognized sort type!!")
        }

        return urlDomainService.getUrls(username, domain, sortType, cursorValue).map { searchHit ->
            val content = searchHit.content
            val visitedTime = getDateFromString(content.visitedTime)
            UrlModel(
                content.siteDomain.name,
                content.url,
                content.keyword,
                (visitedTime / 1000),
                (visitedTime % 1000 * 1000000).toInt(),
                content.visitedTime
            )
        }
    }

    fun getDateFromString(visitedTime: String): Long {
        val datetimeformat: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val dateTime: DateTime = datetimeformat.parseDateTime(visitedTime)
        return dateTime.millis
    }

    override fun getListUrlsByKeyword() {
        TODO("Not yet implemented")
    }
}