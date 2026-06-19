package com.example.bumiku.data.api

import com.example.bumiku.data.model.Task
import retrofit2.http.GET

interface TaskApiService {

    @GET("task.json")
    suspend fun getTasks(): List<Task>
}
