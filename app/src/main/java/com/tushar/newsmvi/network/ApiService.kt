package com.tushar.newsmvi.network

import androidx.lifecycle.LiveData
import com.tushar.newsmvi.model.NewsModel
import com.tushar.newsmvi.util.GenericApiResponse
import retrofit2.http.GET
import com.tushar.newsmvi.BuildConfig

interface ApiService {

    @GET("v2/top-headlines?country=in&apiKey=${BuildConfig.API_KEY}")
    fun getNewsArtcles(): LiveData<GenericApiResponse<NewsModel>>

}