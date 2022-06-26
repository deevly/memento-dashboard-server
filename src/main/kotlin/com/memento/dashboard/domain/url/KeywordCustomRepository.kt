package com.memento.dashboard.domain.url

import org.elasticsearch.search.aggregations.Aggregations
import org.elasticsearch.search.aggregations.BucketOrder

interface KeywordCustomRepository {
    // 키워드 조회, 소트 동적
    fun findAllKeyword(name: String, bucketOrder: BucketOrder, startDate: String, endDate: String): Aggregations?

    // 키워드 검색 결과를 aggregation 조회, 소트 동적
    fun findAllKeywordBySearch(name: String, search: String): Aggregations?
}