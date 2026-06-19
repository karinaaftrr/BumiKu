package com.example.bumiku.data.api

import com.example.bumiku.data.model.News
import retrofit2.http.GET

interface NewsApiService {

    @GET("news.json")
    suspend fun getNews(): List<News>
}