package com.tushar.newsmvi.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tushar.newsmvi.model.NewsModel
import com.tushar.newsmvi.repository.Repository
import com.tushar.newsmvi.ui.state.MainStateEvent
import com.tushar.newsmvi.ui.state.MainViewState
import com.tushar.newsmvi.util.AbsentLiveData
import com.tushar.newsmvi.util.DataState

class MainViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    val viewState: LiveData<MainViewState>
        get() = _viewState

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when(stateEvent){
            is MainStateEvent.FetchMovies ->{
                repository.getBlogPosts()
            }
            is MainStateEvent.None ->{
                AbsentLiveData.create()
            }
        }
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }

    fun setNewsListData(news: NewsModel?){
        val update = getCurrentViewStateOrNew()
        update.newsModel = news
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value ?: MainViewState()
    }
}