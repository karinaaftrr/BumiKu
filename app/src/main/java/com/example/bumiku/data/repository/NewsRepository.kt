package com.example.bumiku.data.repository

import com.example.bumiku.data.api.RetrofitClientNews
import com.example.bumiku.data.model.News

class NewsRepository {

    suspend fun getNews(): List<News> {
        return try {
            RetrofitClientNews.instance.getNews()
        } catch (e: Exception) {
            emptyList()
        }
    }
}