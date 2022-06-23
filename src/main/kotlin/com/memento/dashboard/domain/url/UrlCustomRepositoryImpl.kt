package com.memento.dashboard.domain.url

import mu.KLogging
import org.elasticsearch.index.query.MatchQueryBuilder
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery
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
        val queryBuilder = QueryBuilders.boolQuery().filter(MatchQueryBuilder("user", name))
        val query: Query = templateUrlQuery(queryBuilder, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    override fun findAllUrlWithSiteDomain(
        name: String,
        siteDomain: String,
        direction: Direction,
        cursor: List<String>?,
    ): List<SearchHit<Url>> {
        val queryBuilder = QueryBuilders.boolQuery()
            .filter(MatchQueryBuilder("user", name))
            .filter(MatchQueryBuilder("siteDomain", siteDomain))
        val query: Query = templateUrlQuery(queryBuilder, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    override fun findAllKeywordUrl(
        name: String,
        keyword: String,
        direction: Direction,
        cursor: List<String>?
    ): List<SearchHit<Url>> {
        val queryBuilder = QueryBuilders.boolQuery()
            .filter(MatchQueryBuilder("user", name))
            .filter(MatchQueryBuilder("keyword.keyword", keyword))
        val query: Query = templateUrlQuery(queryBuilder, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    override fun findAllKeywordUrlWithSiteDomain(
        name: String,
        keyword: String,
        siteDomain: String,
        direction: Direction,
        cursor: List<String>?
    ): List<SearchHit<Url>> {
        val queryBuilder = QueryBuilders.boolQuery()
            .filter(MatchQueryBuilder("user", name))
            .filter(MatchQueryBuilder("keyword.keyword", keyword))
            .filter(MatchQueryBuilder("siteDomain", siteDomain))
        val query: Query = templateUrlQuery(queryBuilder, direction, "visitedTime", cursor)

        return elasticsearchOperations.search(query, Url::class.java).searchHits
    }

    private fun templateUrlQuery(
        queryBuilder: QueryBuilder,
        direction: Direction,
        sortBy: String,
        cursor: List<String>?
    ): Query {
        val query: Query = NativeSearchQuery(queryBuilder).apply {
            setPageable<CriteriaQuery>(Pageable.ofSize(10))
            addSort<CriteriaQuery>(Sort.by(direction, sortBy))
            searchAfter = cursor
        }
        return query
    }
}