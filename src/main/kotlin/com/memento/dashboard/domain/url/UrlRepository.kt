package com.memento.dashboard.domain.url

import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : ElasticsearchRepository<Url, Long>, UrlCustomRepository {

    // 특정 키워드에 속한 URL 조회, 사이트 도메인 없는 경우, 소트 동적
    @Query("")
    fun findAllKeywordUrl(name: String): List<Url>

    // 특정 키워드에 속한 URL 조회, 사이트 도메인 있는 경우, 소트 동적
    @Query("")
    fun findAllKeywordUrlWithSiteDomain(name: String): List<Url>
}