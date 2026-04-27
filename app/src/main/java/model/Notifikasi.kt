package com.example.bumiku.model

data class Notifikasi(
    val id: Int,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean = false
)