package com.memento.dashboard.application

import com.memento.dashboard.application.impl.url.ListUrlsByKeywordRequest
import com.memento.dashboard.application.impl.url.ListUrlsRequest

interface UrlService {

    fun getListUrls(listUrlsRequest: ListUrlsRequest)

    fun getListUrlsByKeyword(listUrlsByKeywordRequest: ListUrlsByKeywordRequest)
}