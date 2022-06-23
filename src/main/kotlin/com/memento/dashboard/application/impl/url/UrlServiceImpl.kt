package com.memento.dashboard.application.impl.url

import com.memento.dashboard.application.UrlService
import com.memento.dashboard.domain.url.SiteDomain
import com.memento.dashboard.domain.url.UrlDomainService
import memento.Types.URLCursor.SortType
import org.springframework.stereotype.Service

@Service
class UrlServiceImpl(
    private val urlDomainService: UrlDomainService,
) : UrlService {

    override fun getListUrls(username: String, domain: SiteDomain, sortType: SortType, cursorValue: String): List<UrlModel> {
        validateSortType(sortType)

        return urlDomainService.getUrls(username, domain, sortType, cursorValue).map { searchHit ->
            UrlModel.create(searchHit.content)
        }
    }

    override fun getListUrlsByKeyword(username: String, keyword: String, domain: SiteDomain, sortType: SortType, cursorValue: String): List<UrlModel> {
        validateSortType(sortType)

        return urlDomainService.getUrlsByKeyword(username, keyword, domain, sortType, cursorValue).map { searchHit ->
            UrlModel.create(searchHit.content)
        }
    }

    private fun validateSortType(sortType: SortType) {
        if (sortType == SortType.UNRECOGNIZED) {
            throw Exception("unrecognized sort type!!")
        }
    }
}