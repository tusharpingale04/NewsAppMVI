package com.tushar.newsmvi.di

import android.app.Application
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tushar.newsmvi.BuildConfig
import com.tushar.newsmvi.R
import com.tushar.newsmvi.network.ApiService
import com.tushar.newsmvi.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun provideRetrofitBuilder() : Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideApiService(builder: Retrofit.Builder) : ApiService{
        return builder.build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRequestOptions() : RequestOptions {
        return RequestOptions().error(R.drawable.image_12).placeholder(R.drawable.image_12)
    }

    @Singleton
    @Provides
    fun provideRequestManager(application: Application, requestOptions: RequestOptions) : RequestManager {
        return Glide.with(application).setDefaultRequestOptions(requestOptions)
    }

}