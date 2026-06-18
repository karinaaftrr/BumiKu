package com.example.bumiku.data.firebase

import com.example.bumiku.data.model.GuideProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseGuideService {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun currentUserId(): String {
        return auth.currentUser?.uid.orEmpty()
    }

    suspend fun getReadMaterialIds(): List<String> {
        val userId = currentUserId()

        if (userId.isBlank()) {
            return emptyList()
        }

        val snapshot = firestore
            .collection("users")
            .document(userId)
            .collection("guide_progress")
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val read = document.getBoolean("read") ?: false
            val materialId = document.getString("materialId")

            if (read) materialId else null
        }
    }

    suspend fun saveGuideProgress(
        categoryId: String,
        title: String,
        read: Boolean
    ) {
        val userId = currentUserId()

        if (userId.isBlank()) {
            return
        }

        val materialId = makeMaterialId(
            categoryId = categoryId,
            title = title
        )

        val progress = GuideProgress(
            materialId = materialId,
            categoryId = categoryId,
            title = title,
            read = read,
            updatedAt = System.currentTimeMillis()
        )

        firestore
            .collection("users")
            .document(userId)
            .collection("guide_progress")
            .document(materialId)
            .set(progress, SetOptions.merge())
            .await()
    }

    companion object {
        fun makeMaterialId(
            categoryId: String,
            title: String
        ): String {
            return "${categoryId}_${title.lowercase().replace(" ", "_")}"
        }
    }
}