package com.bm.extremesolution.presentation.search.adapter

import com.bm.extremesolution.data.model.response.SearchResponse

interface SearchListener{
    fun onSearchClick(id :SearchResponse.productItem )
}