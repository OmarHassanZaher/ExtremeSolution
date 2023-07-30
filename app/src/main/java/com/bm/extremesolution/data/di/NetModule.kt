package com.bm.extremesolution

import com.bm.extremesolution.data.remote.ApiServices
import android.content.Context
import androidx.room.Room
import com.bm.extremesolution.data.local.AppDao
import com.bm.extremesolution.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    @Singleton
    fun providesAppDao(appDatabase: AppDatabase): AppDao = appDatabase.appDap()

    @Provides
    @Singleton
    fun providesAppDataBase(@ApplicationContext context: Context):AppDatabase
    = Room.databaseBuilder(context,AppDatabase::class.java,"AppDatabase")
        .allowMainThreadQueries()
        .build()


    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("Language","en")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Singleton
    @Provides
    fun provideHOPApiService(retrofit: Retrofit) : ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}