package com.memento.dashboard.domain.url

import org.elasticsearch.index.query.MatchQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.RangeQueryBuilder
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.Aggregations
import org.elasticsearch.search.aggregations.BucketOrder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class KeywordCustomRepositoryImpl(
    private val elasticsearchOperations: ElasticsearchOperations
): KeywordCustomRepository {

    override fun findAllKeyword(
        name: String,
        bucketOrder: BucketOrder,
        startDate: String,
        endDate: String
    ): Aggregations? {
        val queryBuilder = QueryBuilders.boolQuery()
            .filter(MatchQueryBuilder("user", name))
            .filter(RangeQueryBuilder("visitedTime").gte(startDate).lt(endDate).format("yyyy-MM-dd"))

        val query: Query = NativeSearchQueryBuilder()
            .withMaxResults(0)
            .withQuery(queryBuilder)
            .addAggregation(AggregationBuilders.terms("keywords").field("keyword.keyword")
                .subAggregation(AggregationBuilders.max("lastVisit").field("visitedTime"))
                .order(bucketOrder))
            .build()

        return elasticsearchOperations.search(query, Url::class.java).aggregations
    }

    override fun findAllKeywordBySearch(
        name: String,
        search: String
    ): Aggregations? {
        val queryBuilder = QueryBuilders.boolQuery()
            .must(FunctionScoreQueryBuilder(MatchQueryBuilder("keyword", search)))
            .filter(MatchQueryBuilder("user", name))

        val query: Query = NativeSearchQueryBuilder()
            .withMaxResults(0)
            .withQuery(queryBuilder)
            .addAggregation(AggregationBuilders.terms("keywords").field("keyword.keyword")
                .subAggregation(AggregationBuilders.max("lastVisit").field("visitedTime")))
            .build()

        return elasticsearchOperations.search(query, Url::class.java).aggregations
    }
}