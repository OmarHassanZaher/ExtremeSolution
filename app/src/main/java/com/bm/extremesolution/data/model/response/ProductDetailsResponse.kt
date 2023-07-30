package com.bm.extremesolution.data.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cart_table")
data class ProductDetailsResponse (
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @PrimaryKey(autoGenerate = true)
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
class RatingTypeConverter {
    @TypeConverter
    fun fromRating(rating: ProductDetailsResponse.Rating?): String? {
        return rating?.let { "${it.count},${it.rate}" }
    }

    @TypeConverter
    fun toRating(ratingString: String?): ProductDetailsResponse.Rating? {
        return ratingString?.let {
            val parts = it.split(",")
            if (parts.size == 2) {
                ProductDetailsResponse.Rating(
                    parts[0].toInt(),
                    parts[1].toDouble()
                )
            } else {
                null
            }
        }
    }
}