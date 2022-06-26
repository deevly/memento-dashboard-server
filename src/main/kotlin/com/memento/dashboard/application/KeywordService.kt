package com.memento.dashboard.application

import com.memento.dashboard.application.impl.keyword.KeywordModel
import memento.Types

interface KeywordService {

    fun getListKeywords(username: String, sortType: Types.KeywordCursor.SortType, startDate: String): List<KeywordModel>

    fun getSearchKeywords(username: String, search: String): List<KeywordModel>
}