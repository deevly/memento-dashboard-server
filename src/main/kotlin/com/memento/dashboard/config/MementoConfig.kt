package com.memento.dashboard.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "memento")
data class MementoConfig (
    val opensearch: OpenSearchProperties,
    val kms: KmsProperties,
) {
    data class OpenSearchProperties(
        val address: String,
        val username: String,
        val password: String
    )

    data class KmsProperties(
        val keyId: String
    )
}