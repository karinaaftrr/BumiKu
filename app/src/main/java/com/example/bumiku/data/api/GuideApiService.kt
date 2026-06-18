package com.example.bumiku.data.api

import com.example.bumiku.data.model.WasteCategory
import retrofit2.http.GET

interface GuideApiService {

    @GET("guide.json")
    suspend fun getGuides(): List<WasteCategory>
}