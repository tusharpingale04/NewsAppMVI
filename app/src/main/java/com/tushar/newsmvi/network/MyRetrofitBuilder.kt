package com.tushar.newsmvi.network

import com.tushar.newsmvi.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitBuilder {

    private const val BASE_URL: String = "https://newsapi.org/"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }


    val apiService: ApiService by lazy{
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}