package com.bm.extremesolution.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bm.extremesolution.data.model.response.ProductDetailsResponse
import com.bm.extremesolution.data.model.response.ProductsByCategoryResponse
import com.bm.extremesolution.data.model.response.RatingTypeConverter


@Database(entities = [ProductDetailsResponse::class], version = 1, exportSchema = false)
@TypeConverters(RatingTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDap(): AppDao
}

