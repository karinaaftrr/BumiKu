package com.example.bumiku.data.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("day")
    val day: Int = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("steps")
    val steps: List<String> = emptyList(),

    @SerializedName("tip")
    val tip: String = "",

    @SerializedName("estimasiWaktu")
    val estimasiWaktu: String = "",

    @SerializedName("poin")
    val poin: String = "",

    val isDone: Boolean = false
)
