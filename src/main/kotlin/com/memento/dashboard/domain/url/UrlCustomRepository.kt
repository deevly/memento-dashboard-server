package com.memento.dashboard.domain.url

import org.springframework.data.domain.Sort.Direction
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.stereotype.Repository

@Repository
interface UrlCustomRepository {
    // 사이트 도메인 없는 경우, 소트는 동적으로 적용
    fun findAllUrl(name: String, direction: Direction, cursor: List<String>?): List<SearchHit<Url>>

    // 사이트 도메인 있는 경우, 소트는 동적으로 적용
    fun findAllUrlWithSiteDomain(name: String, siteDomain: String, direction: Direction, cursor: List<String>?): List<SearchHit<Url>>

    // 특정 키워드에 속한 URL 조회, 사이트 도메인 없는 경우, 소트 동적
    fun findAllKeywordUrl(name: String, keyword: String, direction: Direction, cursor: List<String>?): List<SearchHit<Url>>

    // 특정 키워드에 속한 URL 조회, 사이트 도메인 있는 경우, 소트 동적
    fun findAllKeywordUrlWithSiteDomain(name: String, keyword: String, siteDomain: String, direction: Direction, cursor: List<String>?): List<SearchHit<Url>>

    // 키워드 aggregation해서 조회, 소트 동적
    fun findAllKeyword(name: String, sortType: String, direction: Direction, cursor: List<String>?): List<SearchHit<Url>>

    // 키워드 검색 결과를 aggregation 조회, 소트 동적
    fun findAllKeywordBySearch(name: String, regex: String, sortType: String, direction: Direction, cursor: List<String>?): List<SearchHit<Url>>
}