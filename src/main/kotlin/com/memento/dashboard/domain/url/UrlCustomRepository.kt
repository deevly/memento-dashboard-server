package com.memento.dashboard.domain.url

import org.springframework.data.domain.Sort
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Repository

@Repository
interface UrlCustomRepository {
    // 사이트 도메인 없는 경우, 소트는 동적으로 적용
    fun findAllUrl(name: String, direction: Sort.Direction, cursor: List<String>?): List<SearchHit<Url>>

    // 사이트 도메인 있는 경우, 소트는 동적으로 적용
    fun findAllUrlWithSiteDomain(name: String, siteDomain: String, sortType: Sort.Direction, cursor: List<String>?): List<SearchHit<Url>>
}