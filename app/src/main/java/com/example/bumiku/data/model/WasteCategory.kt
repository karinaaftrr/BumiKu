package com.example.bumiku.data.model

import com.google.gson.annotations.SerializedName

data class WasteCategory(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("icon")
    val icon: String = "",

    @SerializedName("imageName")
    val imageName: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("managementItems")
    val managementItems: List<String> = emptyList(),

    @SerializedName("recyclingTips")
    val recyclingTips: List<String> = emptyList()
)