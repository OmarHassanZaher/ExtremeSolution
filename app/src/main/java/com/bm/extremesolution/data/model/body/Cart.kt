package com.bm.extremesolution.data.model.body

data class Cart(
    val id: Int,
    val title: String,
    val price: Double,
    var quantity: Int = 1
)