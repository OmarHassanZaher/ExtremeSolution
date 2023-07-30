package com.bm.extremesolution.domain.usecase

import com.bm.extremesolution.data.model.response.CategoriesResponse
import com.bm.extremesolution.data.remote.ApiServices
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.domain.mapper.ResourceMapper
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: ResourceMapper<CategoriesResponse?>
){
    suspend fun execute(): Resource<CategoriesResponse?> = mapper.map(apiServices.getCategories())
}