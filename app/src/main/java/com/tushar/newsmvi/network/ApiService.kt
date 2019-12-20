package com.tushar.newsmvi.network

import androidx.lifecycle.LiveData
import com.tushar.newsmvi.model.NewsModel
import com.tushar.newsmvi.util.GenericApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("v2/top-headlines?country=in&apiKey=API_KEY")
    fun getNewsArtcles(): LiveData<GenericApiResponse<NewsModel>>

}