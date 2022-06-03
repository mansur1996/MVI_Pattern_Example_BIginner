package com.example.mvi_pattern_example.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_pattern_example.data.repository.Repository
import com.example.mvi_pattern_example.ui.intent.MainIntent
import com.example.mvi_pattern_example.ui.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: Repository) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    val state = MutableStateFlow<MainState>(MainState.Idle)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect() {
                when(it){
                    is MainIntent.FetchUser -> fetchUser()
                }
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            state.value = MainState.Loading
            state.value = try {
                MainState.Users(repository.getUsers())
            }catch (e : Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }

}