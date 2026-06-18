package com.example.bumiku.data.repository

import com.example.bumiku.data.api.RetrofitClientGuide
import com.example.bumiku.data.model.WasteCategory

class GuideRepository {

    suspend fun getGuides(): List<WasteCategory> {
        return try {
            RetrofitClientGuide.instance.getGuides()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getGuideById(categoryId: String?): WasteCategory? {
        return try {
            RetrofitClientGuide.instance.getGuides().find {
                it.id == categoryId
            }
        } catch (e: Exception) {
            null
        }
    }
}