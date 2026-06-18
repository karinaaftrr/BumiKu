package com.example.bumiku.data.api

import com.example.bumiku.data.model.Notification
import retrofit2.http.GET

interface NotificationApiService {

    @GET("notification.json")
    suspend fun getNotifications(): List<Notification>
}