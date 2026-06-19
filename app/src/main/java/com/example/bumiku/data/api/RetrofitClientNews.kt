package com.example.bumiku.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientNews {

    private const val BASE_URL =
        "https://gist.githubusercontent.com/karinaaftrr/3ac5ca7328b5b0a923ca6e43a32b4a87/raw/"

    val instance: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}