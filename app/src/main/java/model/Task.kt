package com.example.bumiku.model

data class Task(
    val id: Int,
    val day: Int,
    val title: String,
    val description: String,
    val steps: List<String>,
    val tip: String,
    val estimasiWaktu: String,
    val poin: String,
    var isDone: Boolean = false
)