package com.example.bumiku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bumiku.data.model.Community
import com.example.bumiku.data.repository.CommunityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WComViewModel(
    private val repository: CommunityRepository = CommunityRepository()
) : ViewModel() {

    private val _listKomunitas = MutableStateFlow<List<Community>>(emptyList())
    val listKomunitas: StateFlow<List<Community>> = _listKomunitas

    private val _selectedCategory = MutableStateFlow("Semua")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCommunities()
    }

    fun loadCommunities() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val data = repository.getCommunitiesWithProgress()

            if (data.isEmpty()) {
                _errorMessage.value =
                    "Data komunitas gagal dimuat. Periksa koneksi internet atau URL community.json."
            } else {
                _listKomunitas.value = data
            }

            _isLoading.value = false
        }
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun getFilteredKomunitas(): List<Community> {
        val category = _selectedCategory.value

        return if (category == "Semua") {
            _listKomunitas.value
        } else {
            _listKomunitas.value.filter {
                it.kategori.equals(category, ignoreCase = true)
            }
        }
    }

    fun getCommunityById(id: Int): Community? {
        return _listKomunitas.value.find {
            it.id == id
        }
    }

    fun daftarKegiatan(
        communityId: Int,
        onSuccess: () -> Unit = {}
    ) {
        updateJoinStatus(
            communityId = communityId,
            joined = true,
            onSuccess = onSuccess
        )
    }

    fun batalkanKegiatan(
        communityId: Int,
        onSuccess: () -> Unit = {}
    ) {
        updateJoinStatus(
            communityId = communityId,
            joined = false,
            onSuccess = onSuccess
        )
    }

    private fun updateJoinStatus(
        communityId: Int,
        joined: Boolean,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isUpdating.value = true
            _errorMessage.value = null

            val community = _listKomunitas.value.find {
                it.id == communityId
            }

            if (community == null) {
                _errorMessage.value = "Data kegiatan tidak ditemukan."
                _isUpdating.value = false
                return@launch
            }

            try {
                val updatedSlotTerisi = repository.updateCommunityJoin(
                    community = community,
                    joined = joined
                )

                _listKomunitas.value = _listKomunitas.value.map { item ->
                    if (item.id == communityId) {
                        item.copy(
                            isJoined = joined,
                            slotTerisi = updatedSlotTerisi
                        )
                    } else {
                        item
                    }
                }

                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Gagal memperbarui partisipasi."
            } finally {
                _isUpdating.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}