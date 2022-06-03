package com.example.mvi_pattern_example.ui.viewstate

import com.example.mvi_pattern_example.data.model.User

sealed class MainState {
    object Idle : MainState() // idle -> bo'sh
    object Loading : MainState()
    data class Users(val user : List<User>) : MainState()
    data class Error(val error : String?) : MainState()
}