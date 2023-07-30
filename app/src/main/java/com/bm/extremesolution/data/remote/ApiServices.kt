package com.bm.extremesolution.data.remote

import com.bm.extremesolution.data.model.response.CategoriesResponse
import com.bm.extremesolution.data.model.response.ProductDetailsResponse
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.data.model.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.*

interface ApiServices {
    @GET("products/categories")
    suspend fun getCategories(): Response<CategoriesResponse?>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): Response<ProductsByCategoryResponse?>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductDetailsResponse?>

    @GET("products")
    suspend fun getSearch(@Query("sort") keyword: String): Response<SearchResponse?>

}