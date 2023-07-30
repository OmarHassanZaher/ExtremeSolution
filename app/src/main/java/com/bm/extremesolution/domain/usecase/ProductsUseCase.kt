package com.bm.extremesolution.domain.usecase

import com.bm.extremesolution.data.local.AppDao
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.data.remote.ApiServices
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.domain.mapper.ResourceMapper
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: ResourceMapper<ProductsByCategoryResponse?>,
    private val appDao: AppDao
){
    suspend fun execute(category: String): Resource<ProductsByCategoryResponse?> = mapper.map(apiServices.getProductsByCategory(category))

}