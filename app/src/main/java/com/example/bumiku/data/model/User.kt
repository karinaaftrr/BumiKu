package com.example.bumiku.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("uid")
    val uid: String = "",

    @SerializedName("fullName")
    val fullName: String = "Pengguna",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("profileImageUrl")
    val profileImageUrl: String = "",

    @SerializedName("profileImageBase64")
    val profileImageBase64: String = ""
) {
    val firstName: String
        get() = fullName.split(" ").firstOrNull() ?: fullName
}