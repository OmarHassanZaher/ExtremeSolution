package com.bm.extremesolution.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bm.extremesolution.data.model.response.ProductDetailsResponse
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductDetails(productDetails: ProductDetailsResponse)

    // Insert a list of product details
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductDetails(productDetailsList: List<ProductDetailsResponse>)

    // Get all product details
    @Query("SELECT * FROM cart_table")
    suspend fun getAllProductDetails(): List<ProductDetailsResponse>

    // Get a single product details by ID
    @Query("SELECT * FROM cart_table WHERE id = :id")
    suspend fun getProductDetailsById(id: Int): ProductDetailsResponse?

}