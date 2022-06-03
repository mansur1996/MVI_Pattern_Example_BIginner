package com.example.mvi_pattern_example.data.api

import com.example.mvi_pattern_example.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers() : List<User>

}