package com.memento.dashboard.domain.url

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "url")
class Url (user: String, content: String, guid: String, siteDomain: SiteDomain){
    @Id
    var id: Long? = null

    var user: String = user
    var content: String = content
    var siteDomain: SiteDomain = siteDomain
    var guid: String = guid
}