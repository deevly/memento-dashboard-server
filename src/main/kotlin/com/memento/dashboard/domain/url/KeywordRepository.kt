package com.memento.dashboard.domain.url

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface KeywordRepository: ElasticsearchRepository<Url, Long>, KeywordCustomRepository