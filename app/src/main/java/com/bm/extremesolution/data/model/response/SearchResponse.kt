package com.bm.extremesolution.data.model.response
import com.google.gson.annotations.SerializedName

class SearchResponse : ArrayList<SearchResponse.productItem>(){
    data class productItem(
        @SerializedName("category")
        val category: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("image")
        val image: String? = null,
        @SerializedName("price")
        val price: Double? = null,
        @SerializedName("rating")
        val rating: Rating? = null,
        @SerializedName("title")
        val title: String? = null
    ) {
        data class Rating(
            @SerializedName("count")
            val count: Int? = null,
            @SerializedName("rate")
            val rate: Double? = null
        )
    }
}