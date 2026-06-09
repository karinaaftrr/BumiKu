package com.example.bumiku.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientCommunity {

    private const val BASE_URL =
        "https://gist.githubusercontent.com/karinaaftrr/8b85fc79bd593e1a58ff09af86bd7af7/raw/bd3a7101e4a4406ba85ad79f8acb39390c7d2e8b/"

    val instance: CommunityApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommunityApiService::class.java)
    }
}