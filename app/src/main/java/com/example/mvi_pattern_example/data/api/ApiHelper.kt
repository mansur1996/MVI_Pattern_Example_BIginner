package com.example.mvi_pattern_example.data.api

import com.example.mvi_pattern_example.data.model.User

interface ApiHelper {
    suspend fun getUsers() : List<User>
}