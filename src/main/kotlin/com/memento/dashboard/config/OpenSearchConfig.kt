package com.memento.dashboard.config

import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.ssl.SSLContextBuilder
import org.apache.http.ssl.SSLContexts
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext


@Configuration
class OpenSearchConfig {

    @Bean
    fun client(): RestHighLevelClient {
        return try {

            val credentialsProvider: CredentialsProvider = BasicCredentialsProvider()
            credentialsProvider.setCredentials(AuthScope.ANY, UsernamePasswordCredentials("admin", "1234"))

            val sslBuilder: SSLContextBuilder = SSLContexts.custom()
                .loadTrustMaterial(
                    null
                ) { _: Array<X509Certificate?>?, _: String? -> true }
            val sslContext: SSLContext = sslBuilder.build()
            val client = RestHighLevelClient(
                RestClient
                    .builder(HttpHost("52.79.44.255", 9200, "https"))
                    .setHttpClientConfigCallback { httpClientBuilder ->
                        httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider)
                            .setSSLContext(sslContext)
                            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                }
                .setRequestConfigCallback { requestConfigBuilder ->
                    requestConfigBuilder.setConnectTimeout(5000)
                        .setSocketTimeout(120000)
                }
            )
            println("elasticsearch client created")
            client
        } catch (e: Exception) {
            println(e)
            throw Exception("Could not create an elasticsearch client!!")
        }
    }

    @Bean
    fun elasticsearchTemplate(): ElasticsearchOperations {
        return ElasticsearchRestTemplate(client())
    }
}