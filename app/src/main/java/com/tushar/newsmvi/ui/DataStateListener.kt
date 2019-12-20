package com.tushar.newsmvi.ui

import com.tushar.newsmvi.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}