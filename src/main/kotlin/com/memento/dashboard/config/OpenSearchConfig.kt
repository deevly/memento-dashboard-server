package com.memento.dashboard.config

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate

@Configuration
class OpenSearchConfig {

//    @Bean
//    fun client(): RestHighLevelClient {
//        val configuration = ClientConfiguration
//            .builder()
//            .connectedTo("15.164.96.210:9200")
//            .build()
//
//        return RestClients.create(configuration).rest()
//    }
//
//    @Bean
//    fun elasticsearchTemplate(): ElasticsearchOperations {
//        return ElasticsearchRestTemplate(client())
//    }
}
