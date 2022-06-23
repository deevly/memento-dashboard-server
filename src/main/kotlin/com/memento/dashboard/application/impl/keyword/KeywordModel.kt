package com.memento.dashboard.application.impl.keyword

import org.elasticsearch.search.aggregations.bucket.terms.Terms

data class KeywordModel(
    val keyword: String,
    val count: Int,
    val seconds: Long,
    val nanos: Int
) {

    companion object {
        fun create(keywordBucket: Terms.Bucket, lastVisitedTime: Double): KeywordModel {
            return KeywordModel(
                keywordBucket.keyAsString,
                keywordBucket.docCount.toInt(),
                (lastVisitedTime / 1000).toLong(),
                (lastVisitedTime % 1000 * 1000000).toInt()
            )
        }
    }
}