package com.example.bumiku.data.firebase

import com.example.bumiku.data.model.ProgressTask
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseTaskService {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun currentUserId(): String {
        return auth.currentUser?.uid.orEmpty()
    }

    suspend fun getDoneTaskIds(): List<Int> {
        val userId = currentUserId()

        if (userId.isBlank()) {
            return emptyList()
        }

        val snapshot = firestore
            .collection("users")
            .document(userId)
            .collection("task_progress")
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val done = document.getBoolean("done") ?: false
            val taskId = document.getLong("taskId")?.toInt()

            if (done) {
                taskId
            } else {
                null
            }
        }
    }

    suspend fun saveTaskProgress(
        taskId: Int,
        done: Boolean
    ) {
        val userId = currentUserId()

        if (userId.isBlank()) {
            throw Exception("User belum login. Silakan login ulang.")
        }

        val progress = ProgressTask(
            taskId = taskId,
            done = done,
            updatedAt = System.currentTimeMillis()
        )

        firestore
            .collection("users")
            .document(userId)
            .collection("task_progress")
            .document(taskId.toString())
            .set(progress, SetOptions.merge())
            .await()
    }
}
