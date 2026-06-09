package com.example.bumiku.data.api

import com.example.bumiku.data.model.Community
import retrofit2.http.GET

interface CommunityApiService {

    @GET("community.json")
    suspend fun getCommunities(): List<Community>
}