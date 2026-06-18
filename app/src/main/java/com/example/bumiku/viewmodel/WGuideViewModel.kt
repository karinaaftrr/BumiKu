package com.example.bumiku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bumiku.data.model.WasteCategory
import com.example.bumiku.data.repository.GuideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WGuideViewModel(
    private val repository: GuideRepository = GuideRepository()
) : ViewModel() {

    private val _categories = MutableStateFlow<List<WasteCategory>>(emptyList())
    val categories: StateFlow<List<WasteCategory>> = _categories

    private val _readMaterialIds = MutableStateFlow<List<String>>(emptyList())
    val readMaterialIds: StateFlow<List<String>> = _readMaterialIds

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadGuides()
    }

    fun loadGuides() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val guideData = repository.getGuides()
            _categories.value = guideData

            if (guideData.isEmpty()) {
                _errorMessage.value = "Data WGuide gagal dimuat. Cek koneksi internet kamu."
            }

            _isLoading.value = false
        }
    }

    fun getCategoryById(categoryId: String?): WasteCategory? {
        return _categories.value.find { it.id == categoryId }
    }

    fun markAsRead(categoryId: String, title: String) {
        val materialId = makeMaterialId(categoryId, title)
        if (!_readMaterialIds.value.contains(materialId)) {
            _readMaterialIds.value = _readMaterialIds.value + materialId
        }
    }

    fun isRead(categoryId: String, title: String): Boolean {
        val materialId = makeMaterialId(categoryId, title)
        return _readMaterialIds.value.contains(materialId)
    }

    private fun makeMaterialId(categoryId: String, title: String): String {
        return "${categoryId}_${title}"
    }
}