package com.example.mvi_pattern_example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvi_pattern_example.data.api.ApiHelper
import com.example.mvi_pattern_example.data.api.ApiService
import com.example.mvi_pattern_example.data.repository.Repository

/**
 * Biz bu klassda viewModel ni ishga tushiramiz va ViewModel obyektini qaytaramiz.
 */

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(Repository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}