package com.example.bumiku.data.repository

import com.example.bumiku.data.api.RetrofitClientNotification
import com.example.bumiku.data.model.Notification

class NotificationRepository {

    suspend fun getNotifications(): List<Notification> {
        return try {
            RetrofitClientNotification.instance.getNotifications()
        } catch (e: Exception) {
            emptyList()
        }
    }
}