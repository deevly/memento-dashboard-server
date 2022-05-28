package com.memento.dashboard.application

import com.memento.dashboard.application.impl.keyword.ListKeywordRequest
import com.memento.dashboard.application.impl.keyword.SearchKeywordRequest

interface KeywordService {

    fun getListKeywords(listKeywordRequest: ListKeywordRequest)

    fun getSearchKeywords(searchKeywordRequest: SearchKeywordRequest)
}