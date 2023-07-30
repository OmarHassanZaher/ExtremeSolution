package com.bm.extremesolution.data.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Json


class ProductsByCategoryResponse : ArrayList<ProductsByCategoryResponse.ProductsByCategoryResponseItem>(){
    data class ProductsByCategoryResponseItem(
        @field:Json(name = "category")
        val category: String? = null,
        @field:Json(name="description")
        val description: String? = null,
        @PrimaryKey(autoGenerate = true)
        @field:Json(name="id")
        val id: Int? = null,
        @field:Json(name="image")
        val image: String? = null,
        @field:Json(name ="price")
        val price: Double? = null,
        @field:Json(name="rating")
        val rating: Rating? = null,
        @field:Json(name="title")
        val title: String? = null
    ) {
        data class Rating(
            @field:Json(name="count")
            val count: Int? = null,
            @field:Json(name="rate")
            val rate: Double? = null
        )
    }
}
