package com.example.bumiku.model
import androidx.annotation.DrawableRes
data class Komunitas(
    val judul: String,
    val kategori: String,
    val tanggal: String,
    val lokasi: String,
    val penyelenggara: String,
    val slotTerisi: Int,
    val totalSlot: Int,
    @DrawableRes val gambar: Int,
    var isJoined: Boolean = false
)
