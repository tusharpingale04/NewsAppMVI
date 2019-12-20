package com.tushar.newsmvi.ui.state

sealed class MainStateEvent {

    object FetchMovies : MainStateEvent()

    object None: MainStateEvent()
}