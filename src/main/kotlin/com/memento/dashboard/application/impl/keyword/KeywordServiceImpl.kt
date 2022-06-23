package com.memento.dashboard.application.impl.keyword

import com.memento.dashboard.application.KeywordService
import com.memento.dashboard.domain.url.KeywordDomainService
import memento.Types.KeywordCursor.SortType
import org.elasticsearch.search.aggregations.bucket.terms.Terms
import org.elasticsearch.search.aggregations.metrics.ParsedMax
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class KeywordServiceImpl(
    val keywordDomainService: KeywordDomainService
) : KeywordService {

    override fun getListKeywords(username: String, sortType: SortType, startDate: String): List<KeywordModel> {
        validateSortType(sortType)

        val date: LocalDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val terms: Terms = keywordDomainService.getKeywords(username, sortType, date, date.plusDays(1L))

        return terms.buckets.map { bucket ->
            val parsedMax: ParsedMax = bucket.aggregations.asList()[0] as? ParsedMax ?: ParsedMax()
            KeywordModel.create(bucket, parsedMax.value)
        }
    }

    override fun getSearchKeywords(username: String, search: String): List<KeywordModel> {
        val terms: Terms = keywordDomainService.getKeywordBySearch(username, search)

        return terms.buckets.map { bucket ->
            val parsedMax: ParsedMax = bucket.aggregations.asList()[0] as? ParsedMax ?: ParsedMax()
            KeywordModel.create(bucket, parsedMax.value)
        }
    }

    private fun validateSortType(sortType: SortType) {
        if (sortType == SortType.UNRECOGNIZED) {
            throw Exception("unrecognized sort type!!")
        }
    }
}