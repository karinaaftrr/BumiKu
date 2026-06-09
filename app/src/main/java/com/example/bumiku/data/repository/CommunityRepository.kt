package com.example.bumiku.data.repository

import com.example.bumiku.data.api.RetrofitClientCommunity
import com.example.bumiku.data.firebase.FirebaseCommunityService
import com.example.bumiku.data.model.Community

class CommunityRepository(
    private val firebaseCommunityService: FirebaseCommunityService =
        FirebaseCommunityService()
) {

    suspend fun getCommunitiesWithProgress(): List<Community> {
        val communities = try {
            RetrofitClientCommunity.instance.getCommunities()
        } catch (e: Exception) {
            emptyList()
        }

        if (communities.isEmpty()) {
            return emptyList()
        }

        val joinedIds = try {
            firebaseCommunityService.getJoinedCommunityIds()
        } catch (e: Exception) {
            emptyList()
        }

        val firebaseSlotMap = try {
            firebaseCommunityService.getCommunitySlotMap()
        } catch (e: Exception) {
            emptyMap()
        }

        return communities.map { community ->
            community.copy(
                isJoined = joinedIds.contains(community.id),
                slotTerisi = firebaseSlotMap[community.id] ?: community.slotTerisi
            )
        }
    }

    suspend fun updateCommunityJoin(
        community: Community,
        joined: Boolean
    ): Int {
        return firebaseCommunityService.updateCommunityJoin(
            communityId = community.id,
            joined = joined,
            initialSlotTerisi = community.slotTerisi,
            totalSlot = community.totalSlot
        )
    }
}
