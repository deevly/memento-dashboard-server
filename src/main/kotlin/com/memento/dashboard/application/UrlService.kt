package com.memento.dashboard.application

import com.memento.dashboard.application.impl.url.UrlModel
import com.memento.dashboard.domain.url.SiteDomain
import memento.Types.URLCursor.SortType

interface UrlService {

    fun getListUrls(username: String, domain: SiteDomain, type: SortType, cursorValue: String) : List<UrlModel>

    fun getListUrlsByKeyword()
}