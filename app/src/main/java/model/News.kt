package com.example.bumiku.model
import androidx.annotation.DrawableRes

data class News(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    @DrawableRes val gambar: Int
)