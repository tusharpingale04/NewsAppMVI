package com.tushar.newsmvi.repository

import androidx.lifecycle.LiveData
import com.tushar.newsmvi.model.NewsModel
import com.tushar.newsmvi.network.MyRetrofitBuilder
import com.tushar.newsmvi.ui.state.MainViewState
import com.tushar.newsmvi.util.ApiSuccessResponse
import com.tushar.newsmvi.util.DataState
import com.tushar.newsmvi.util.GenericApiResponse

object Repository {

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
                return MyRetrofitBuilder.apiService.getNewsArtcles()
            }

        }.asLiveData()
    }
}




























