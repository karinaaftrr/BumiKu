package com.example.bumiku.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientTask {

    private const val BASE_URL =
        "https://gist.githubusercontent.com/karinaaftrr/a94e28eee909cd8da10c8e1216ff2dc3/raw/"

    val instance: TaskApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TaskApiService::class.java)
    }
}
