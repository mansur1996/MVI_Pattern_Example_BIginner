package com.example.mvi_pattern_example.data.repository

import com.example.mvi_pattern_example.data.api.ApiService

class Repository(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
}