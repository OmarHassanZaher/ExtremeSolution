package com.bm.extremesolution.domain.entity

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("productId")
    val productId: Int? = null,
    @SerializedName("quantity")
    val quantity: Int? = null
)