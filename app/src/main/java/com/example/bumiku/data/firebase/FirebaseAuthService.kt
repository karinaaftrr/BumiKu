package com.example.bumiku.data.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class FirebaseAuthService {

    private val auth = FirebaseAuth.getInstance()

    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): AuthResult {
        val result = auth.createUserWithEmailAndPassword(email, password).await()

        val profileUpdates = userProfileChangeRequest {
            displayName = fullName
        }

        result.user?.updateProfile(profileUpdates)?.await()
        result.user?.reload()?.await()

        return result
    }

    suspend fun login(
        email: String,
        password: String
    ): AuthResult {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        result.user?.reload()?.await()
        return result
    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}