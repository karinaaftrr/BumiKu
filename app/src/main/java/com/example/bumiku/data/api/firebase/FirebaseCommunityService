package com.example.bumiku.data.firebase

import com.example.bumiku.data.model.CommunityProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseCommunityService {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun currentUserId(): String {
        return auth.currentUser?.uid.orEmpty()
    }

    suspend fun getJoinedCommunityIds(): List<Int> {
        val userId = currentUserId()

        if (userId.isBlank()) {
            return emptyList()
        }

        val snapshot = firestore
            .collection("users")
            .document(userId)
            .collection("community_progress")
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val joined = document.getBoolean("joined") ?: false
            val communityId = document.getLong("communityId")?.toInt()

            if (joined) {
                communityId
            } else {
                null
            }
        }
    }

    suspend fun getCommunitySlotMap(): Map<Int, Int> {
        val snapshot = firestore
            .collection("community_slots")
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val communityId = document.getLong("communityId")?.toInt()
            val slotTerisi = document.getLong("slotTerisi")?.toInt()

            if (communityId != null && slotTerisi != null) {
                communityId to slotTerisi
            } else {
                null
            }
        }.toMap()
    }

    suspend fun updateCommunityJoin(
        communityId: Int,
        joined: Boolean,
        initialSlotTerisi: Int,
        totalSlot: Int
    ): Int {
        val userId = currentUserId()

        if (userId.isBlank()) {
            throw Exception("User belum login. Silakan login ulang.")
        }

        val userProgressRef = firestore
            .collection("users")
            .document(userId)
            .collection("community_progress")
            .document(communityId.toString())

        val slotRef = firestore
            .collection("community_slots")
            .document(communityId.toString())

        return firestore.runTransaction { transaction ->
            val userProgressSnapshot = transaction.get(userProgressRef)
            val alreadyJoined =
                userProgressSnapshot.getBoolean("joined") ?: false

            val slotSnapshot = transaction.get(slotRef)

            val currentSlotTerisi = if (slotSnapshot.exists()) {
                slotSnapshot.getLong("slotTerisi")?.toInt() ?: initialSlotTerisi
            } else {
                initialSlotTerisi
            }

            val currentTotalSlot = if (slotSnapshot.exists()) {
                slotSnapshot.getLong("totalSlot")?.toInt() ?: totalSlot
            } else {
                totalSlot
            }

            val newSlotTerisi = when {
                joined && alreadyJoined -> {
                    currentSlotTerisi
                }

                joined && currentSlotTerisi >= currentTotalSlot -> {
                    throw Exception("Slot kegiatan sudah penuh.")
                }

                joined -> {
                    currentSlotTerisi + 1
                }

                !joined && !alreadyJoined -> {
                    currentSlotTerisi
                }

                else -> {
                    (currentSlotTerisi - 1).coerceAtLeast(0)
                }
            }

            val progress = CommunityProgress(
                communityId = communityId,
                joined = joined,
                updatedAt = System.currentTimeMillis()
            )

            transaction.set(
                userProgressRef,
                progress,
                SetOptions.merge()
            )

            transaction.set(
                slotRef,
                mapOf(
                    "communityId" to communityId,
                    "slotTerisi" to newSlotTerisi,
                    "totalSlot" to currentTotalSlot,
                    "updatedAt" to System.currentTimeMillis()
                ),
                SetOptions.merge()
            )

            newSlotTerisi
        }.await()
    }
}
