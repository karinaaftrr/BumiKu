package com.example.bumiku.model

import androidx.annotation.DrawableRes

data class WasteCategory(
    val id: String,
    val name: String,
    val icon: String,
    @DrawableRes val imageRes: Int,
    val description: String,
    val managementItems: List<String>,
    val recyclingTips: List<String>
)