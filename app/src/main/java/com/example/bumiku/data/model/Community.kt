package com.example.bumiku.data.model

import com.google.gson.annotations.SerializedName

data class Community(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("judul")
    val judul: String = "",

    @SerializedName("kategori")
    val kategori: String = "",

    @SerializedName("tanggal")
    val tanggal: String = "",

    @SerializedName("eventTime")
    val eventTime: String = "",

    @SerializedName("lokasi")
    val lokasi: String = "",

    @SerializedName("penyelenggara")
    val penyelenggara: String = "",

    @SerializedName("slotTerisi")
    val slotTerisi: Int = 0,

    @SerializedName("totalSlot")
    val totalSlot: Int = 0,

    @SerializedName("imageName")
    val imageName: String = "",

    val isJoined: Boolean = false
)
