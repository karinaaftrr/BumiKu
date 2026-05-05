package com.example.bumiku.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bumiku.model.Community
import com.example.bumiku.model.CommunitySource
import com.example.bumiku.model.Task
import com.example.bumiku.model.TaskSource

class KomunitasViewModel : ViewModel() {

    var selectedDayInTracker by mutableIntStateOf(1)

    private val _allTasks = mutableStateListOf<Task>().apply {
        addAll(TaskSource.dummyTasks)
    }
    val allTasks: List<Task> get() = _allTasks

    private val _listKomunitas = mutableStateListOf<Community>().apply {
        addAll(CommunitySource.daftarKomunitas)
    }
    val listKomunitas: List<Community> get() = _listKomunitas

    var selectedCategory by mutableStateOf("Semua")

    val filteredKomunitas: List<Community>
        get() = if (selectedCategory == "Semua") {
            _listKomunitas
        } else {
            _listKomunitas.filter {
                it.kategori.equals(selectedCategory, ignoreCase = true)
            }
        }

    fun toggleTask(id: Int) {
        val index = _allTasks.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = _allTasks[index]
            _allTasks[index] = current.copy(isDone = !current.isDone)
        }
    }

    fun completeTask(taskId: Int) {
        val index = _allTasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            val current = _allTasks[index]
            _allTasks[index] = current.copy(isDone = true)
        }
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
                    slotTerisi = (item.slotTerisi - 1).coerceAtLeast(0),
                    isJoined = false
                )
            }
        }
    }
}