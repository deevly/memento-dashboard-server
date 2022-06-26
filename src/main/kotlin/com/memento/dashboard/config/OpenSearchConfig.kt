package com.memento.dashboard.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.kms.AWSKMS
import com.amazonaws.services.kms.AWSKMSClientBuilder
import com.amazonaws.services.kms.model.AWSKMSException
import com.amazonaws.services.kms.model.DecryptRequest
import com.amazonaws.services.kms.model.EncryptionAlgorithmSpec
import mu.KLogging
import org.apache.commons.codec.binary.Base64
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
import java.nio.ByteBuffer
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext


@Configuration
class OpenSearchConfig (
    private val mementoConfig: MementoConfig
    ) {

    val decryptedUsername = getPlainText(mementoConfig.opensearch.username, mementoConfig.kms.keyId)
    val decryptedPassword = getPlainText(mementoConfig.opensearch.password, mementoConfig.kms.keyId)

    @Bean
    fun client(): RestHighLevelClient {
        return try {

            val credentialsProvider: CredentialsProvider = BasicCredentialsProvider()
            credentialsProvider.setCredentials(AuthScope.ANY, UsernamePasswordCredentials(decryptedUsername, decryptedPassword))

            val sslBuilder: SSLContextBuilder = SSLContexts.custom()
                .loadTrustMaterial(
                    null
                ) { _: Array<X509Certificate?>?, _: String? -> true }
            val sslContext: SSLContext = sslBuilder.build()
            val client = RestHighLevelClient(
                RestClient
                    .builder(HttpHost(mementoConfig.opensearch.address, 9200, "https"))
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

    companion object {
        private val logger = KLogging().logger

        fun getPlainText(encryptedText: String, keyId: String) : String {

            val accessKey = System.getProperty("aws.key.access")
            val secretKey = System.getProperty("aws.key.secret")

            try {
                val awsCredentials: AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
                val kmsClient: AWSKMS = AWSKMSClientBuilder
                    .standard()
                    .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.AP_NORTHEAST_2)
                    .build()

                val usernameDecryptRequest: DecryptRequest = DecryptRequest()
                    .withCiphertextBlob(ByteBuffer.wrap(Base64.decodeBase64(encryptedText)))
                    .withKeyId(keyId)
                    .withEncryptionAlgorithm(EncryptionAlgorithmSpec.RSAES_OAEP_SHA_256)

                val plainText: ByteBuffer = kmsClient.decrypt(usernameDecryptRequest).plaintext

                return String(plainText.array())
            } catch (e : AWSKMSException) {
                logger.error { String.format("KMS decryption exception : ${e.message}") }
                return ""
            }
        }
    }
}