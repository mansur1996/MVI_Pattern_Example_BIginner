package com.example.mvi_pattern_example.data.api

import com.example.mvi_pattern_example.data.model.User

/**
 * List<Users> ro'yxatini olish uchun shu Interface ni implement qilib oluvchi
 */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }

}