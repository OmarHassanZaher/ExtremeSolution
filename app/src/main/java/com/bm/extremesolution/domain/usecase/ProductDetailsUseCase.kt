package com.bm.extremesolution.domain.usecase

import com.bm.extremesolution.data.local.AppDao
import com.bm.extremesolution.data.model.response.ProductDetailsResponse
import com.bm.extremesolution.data.remote.ApiServices
import com.bm.extremesolution.domain.entity.Resource
import com.bm.extremesolution.domain.mapper.ResourceMapper
import javax.inject.Inject

class ProductDetailsUseCase @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: ResourceMapper<ProductDetailsResponse?>,
    private val appDao: AppDao
) {
    suspend fun execute(id: Int): Resource<ProductDetailsResponse?> {
        val productDetails = appDao.getProductDetailsById(id)
        if (productDetails != null) {
            return Resource.success(productDetails)
        }

        val apiResult = apiServices.getProductById(id)
        if (apiResult.isSuccessful) {
            val productDetailsResponse = apiResult.body()
            if (productDetailsResponse != null) {
                appDao.insertProductDetails(productDetailsResponse)
                return Resource.success(productDetailsResponse)
            }
        }

        return Resource.apiError(apiResult.message(), apiResult.code())
    }
    suspend fun getAllProductDetails(): Resource<List<ProductDetailsResponse>?> {
        val productDetailsList = appDao.getAllProductDetails()
        return if (productDetailsList.isNotEmpty()) {
            Resource.success(productDetailsList)
        } else {
            Resource.empty()
        }
    }

}