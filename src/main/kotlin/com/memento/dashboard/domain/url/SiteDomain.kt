package com.memento.dashboard.domain.url

enum class SiteDomain(
    val topic: String,
    val prefixUrl: String
) {

    GITHUB("github", "https://github.com"),
    STACKOF("stackOverflow", "https://stackoverflow.com"),
    OTHERS("others", "etc");

    companion object {

        private val map = values().associateBy(SiteDomain::prefixUrl)
        fun fromPrefixUrl(prefixUrl: String) = map[prefixUrl] ?: OTHERS
    }
}
