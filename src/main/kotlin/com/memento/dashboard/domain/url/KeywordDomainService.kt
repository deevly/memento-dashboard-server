package com.memento.dashboard.domain.url

import memento.Types.KeywordCursor.SortType
import org.elasticsearch.search.aggregations.BucketOrder
import org.elasticsearch.search.aggregations.bucket.terms.Terms
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class KeywordDomainService(
    val keywordRepository: KeywordRepository
) {
    fun getKeywords(
        username: String,
        sortType: SortType,
        startDate: LocalDate,
        endDate: LocalDate
    ): Terms {

        return keywordRepository.findAllKeyword(
            username,
            getBucketOrder(sortType),
            startDate.toString(),
            endDate.toString()
        )?.get("keywords") ?: throw Exception("Keyword Not Found")
    }

    fun getKeywordBySearch(
        username: String,
        search: String
    ): Terms {

        return keywordRepository.findAllKeywordBySearch(
            username,
            search
        )?.get("keywords") ?: throw Exception("Keyword Not Found")
    }

    private fun getBucketOrder(keywordSortType: SortType): BucketOrder {
        return when (keywordSortType) {
            SortType.ALPHABETICAL_ORDER -> {
                BucketOrder.key(true)
            }
            SortType.URL_COUNT_DESC -> {
                BucketOrder.count(false)
            }
            SortType.VISITED_TIME -> {
                BucketOrder.aggregation("lastVisit", false)
            }
            else -> {
                BucketOrder.count(false)
            }
        }
    }
}