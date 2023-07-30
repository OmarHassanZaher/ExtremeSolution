package com.bm.extremesolution.domain.usecase

import com.bm.extremesolution.data.model.response.SearchResponse
import com.bm.extremesolution.data.remote.ApiServices
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.domain.mapper.ResourceMapper
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: ResourceMapper<SearchResponse?>
){
    suspend fun execute(keyword: String): Resource<SearchResponse?> = mapper.map(apiServices.getSearch(keyword))
}