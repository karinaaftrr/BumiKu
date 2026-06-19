package com.example.bumiku.data.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("content")
    val content: String = "",

    @SerializedName("date")
    val date: String = "",

    @SerializedName("imageName")
    val imageName: String = ""
)