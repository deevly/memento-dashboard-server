package com.memento.dashboard.domain.url

import mu.KLogging
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UrlCustomRepositoryImpl(
    private val elasticsearchOperations: ElasticsearchOperations
) : UrlCustomRepository {

    private val logger = KLogging().logger

    override fun findAllUrl(
        name: String,
        direction: Direction,
        cursor: List<String>?,
    ): List<SearchHit<Url>> {
        val criteria = Criteria.where("user").matches(name)
        val query: Query = templateQuery(criteria, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    override fun findAllUrlWithSiteDomain(
        name: String,
        siteDomain: String,
        direction: Direction,
        cursor: List<String>?,
    ): List<SearchHit<Url>> {
        val criteria = Criteria.where("user").matches(name).and(
            Criteria.where("siteDomain").matches(siteDomain)
        )
        val query: Query = templateQuery(criteria, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    override fun findAllKeywordUrl(
        name: String,
        keyword: String,
        direction: Direction,
        cursor: List<String>?
    ): List<SearchHit<Url>> {
        val criteria = Criteria.where("user").matches(name).and(
            Criteria.where("keyword.keyword").matches(keyword))
        val query: Query = templateQuery(criteria, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    override fun findAllKeywordUrlWithSiteDomain(
        name: String,
        keyword: String,
        siteDomain: String,
        direction: Direction,
        cursor: List<String>?
    ): List<SearchHit<Url>> {
        val criteria = Criteria.where("user").matches(name)
            .and(Criteria.where("keyword.keyword").matches(keyword)
            ).and(Criteria.where("siteDomain").matches(siteDomain))
        val query: Query = templateQuery(criteria, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    private fun templateQuery(
        criteria: Criteria,
        direction: Direction,
        sortBy: String,
        cursor: List<String>?
    ): Query {
        val query: Query = CriteriaQuery(criteria).apply {
            setPageable<CriteriaQuery>(Pageable.ofSize(20))
            addSort<CriteriaQuery>(Sort.by(direction, sortBy))
            searchAfter = cursor
        }
        return query
    }

    override fun findAllKeyword(
        name: String,
        sortType: String,
        direction: Direction,
        cursor: List<String>?
    ): List<SearchHit<Url>> {
        TODO("Not yet implemented")
    }

    override fun findAllKeywordBySearch(
        name: String,
        regex: String,
        sortType: String,
        direction: Direction,
        cursor: List<String>?
    ): List<SearchHit<Url>> {
        TODO("Not yet implemented")
    }
}