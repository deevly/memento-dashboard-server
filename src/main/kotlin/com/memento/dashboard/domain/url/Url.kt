package com.memento.dashboard.domain.url

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "memento")
class Url (user: String,
           url: String,
           siteDomain: SiteDomain,
           keyword: String,
           visitedTime: String
){
    @Id
    var id: String? = null

    var user: String = user
    var url: String = url
    var siteDomain: SiteDomain = siteDomain
    var keyword: String = keyword
    var visitedTime: String = visitedTime
}