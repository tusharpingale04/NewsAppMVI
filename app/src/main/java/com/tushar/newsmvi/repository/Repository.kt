package com.tushar.newsmvi.repository

import androidx.lifecycle.LiveData
import com.tushar.newsmvi.model.NewsModel
import com.tushar.newsmvi.network.ApiService
import com.tushar.newsmvi.ui.state.MainViewState
import com.tushar.newsmvi.util.ApiSuccessResponse
import com.tushar.newsmvi.util.DataState
import com.tushar.newsmvi.util.GenericApiResponse
import javax.inject.Inject

class Repository @Inject constructor(val apiService: ApiService) {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object: NetworkBoundResource<NewsModel, MainViewState>(){

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<NewsModel>) {
                result.value = DataState.data(
                    null,
                    MainViewState(
                        newsModel = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<NewsModel>> {
                return apiService.getNewsArtcles()
            }

        }.asLiveData()
    }
}




























