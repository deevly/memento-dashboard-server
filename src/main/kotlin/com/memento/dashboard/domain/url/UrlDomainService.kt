package com.memento.dashboard.domain.url

import memento.Types.URLCursor.SortType
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class UrlDomainService(
    private val urlRepository: UrlRepository
) {

    fun getUrls(
        username: String,
        siteDomain: SiteDomain,
        urlSortType: SortType,
        cursor: String
    ): List<SearchHit<Url>> {

        return if (siteDomain == SiteDomain.ALL) {
            urlRepository.findAllUrl(
                username,
                getDirection(urlSortType),
                (if (StringUtils.hasText(cursor)) listOf(cursor) else null)
            )
        } else {
            urlRepository.findAllUrlWithSiteDomain(
                username,
                siteDomain.name,
                getDirection(urlSortType),
                if (StringUtils.hasText(cursor)) listOf(cursor) else null
            )
        }
    }

    private fun getDirection(urlSortType: SortType): Direction {
        return when (urlSortType) {
            SortType.RECENT_TIME_ASC -> {
                Direction.ASC
            }
            SortType.RECENT_TIME_DESC -> {
                Direction.DESC
            }
            else -> {
                Direction.DESC
            }
        }
    }
}