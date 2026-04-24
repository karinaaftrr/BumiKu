package com.example.bumiku.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.bumiku.model.Komunitas
import com.example.bumiku.model.KomunitasDetail

class KomunitasViewModel : ViewModel() {
    private val _listKomunitas = mutableStateListOf<Komunitas>().apply {
        addAll(KomunitasDetail.daftarKomunitas)
    }

    val listKomunitas: List<Komunitas> get() = _listKomunitas
    var selectedCategory by mutableStateOf("Semua")

    val filteredKomunitas: List<Komunitas>
        get() = if (selectedCategory == "Semua") {
            _listKomunitas
        } else {
            _listKomunitas.filter { it.kategori.equals(selectedCategory, ignoreCase = true) }
        }

    fun tambahPartisipan(judul: String) {
        val index = _listKomunitas.indexOfFirst { it.judul == judul }
        if (index != -1) {
            val item = _listKomunitas[index]
            if (!item.isJoined && item.slotTerisi < item.totalSlot) {
                _listKomunitas[index] = item.copy(
                    slotTerisi = item.slotTerisi + 1,
                    isJoined = true
                )
            }
        }
    }

    fun kurangiPartisipan(judul: String) {
        val index = _listKomunitas.indexOfFirst { it.judul == judul }
        if (index != -1) {
            val item = _listKomunitas[index]
            if (item.isJoined) {
                _listKomunitas[index] = item.copy(
                    slotTerisi = item.slotTerisi - 1,
                    isJoined = false
                )
            }
        }
    }
}