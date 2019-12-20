package com.tushar.newsmvi.ui.state

sealed class MainStateEvent {

    object FetchNews : MainStateEvent()

    object None: MainStateEvent()
}