package com.bm.extremesolution.presentation.home.adapter

import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse

interface ProductListListener  {
    fun onProductListClick(id: ProductsByCategoryResponse.ProductsByCategoryResponseItem)
}