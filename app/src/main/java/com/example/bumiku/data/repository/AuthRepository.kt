package com.example.bumiku.data.repository

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.bumiku.data.firebase.FirebaseAuthService
import com.example.bumiku.data.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class AuthRepository {

    private val authService = FirebaseAuthService()
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun isLoggedIn(): Boolean = authService.isLoggedIn()

    suspend fun getCurrentUser(): User? {
        val firebaseUser = authService.getCurrentUser() ?: return null

        return try {
            val snapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            if (snapshot.exists()) {
                snapshot.toObject(User::class.java)?.copy(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: ""
                )
            } else {
                User(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Pengguna",
                    email = firebaseUser.email ?: ""
                )
            }
        } catch (e: Exception) {
            User(
                uid = firebaseUser.uid,
                fullName = firebaseUser.displayName ?: "Pengguna",
                email = firebaseUser.email ?: ""
            )
        }
    }

    suspend fun register(fullName: String, email: String, password: String): Result<User> {
        return try {
            val result = authService.register(fullName, email, password)
            val firebaseUser = result.user ?: return Result.failure(Exception("Registrasi gagal"))

            val user = User(
                uid = firebaseUser.uid,
                fullName = fullName,
                email = firebaseUser.email ?: email
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(user)
                .await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = authService.login(email, password)
            val firebaseUser = result.user ?: return Result.failure(Exception("Login gagal"))

            val snapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val user = if (snapshot.exists()) {
                snapshot.toObject(User::class.java)?.copy(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: email
                ) ?: User(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Pengguna",
                    email = firebaseUser.email ?: email
                )
            } else {
                val newUser = User(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Pengguna",
                    email = firebaseUser.email ?: email
                )

                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(newUser)
                    .await()

                newUser
            }

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithCredential(credential: AuthCredential): Result<User> {
        return try {
            val result = auth.signInWithCredential(credential).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("Login gagal"))

            val snapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val user = if (snapshot.exists()) {
                snapshot.toObject(User::class.java)?.copy(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: ""
                ) ?: User(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Pengguna",
                    email = firebaseUser.email ?: ""
                )
            } else {
                val newUser = User(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Pengguna",
                    email = firebaseUser.email ?: ""
                )

                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(newUser)
                    .await()

                newUser
            }

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signInWithGitHub(activity: Activity): Result<User> {
        return try {
            val provider = OAuthProvider.newBuilder("github.com")
            val result = auth.startActivityForSignInWithProvider(activity, provider.build()).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("GitHub login gagal"))

            val snapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val user = if (snapshot.exists()) {
                snapshot.toObject(User::class.java)?.copy(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: ""
                ) ?: User(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Pengguna",
                    email = firebaseUser.email ?: ""
                )
            } else {
                val newUser = User(
                    uid = firebaseUser.uid,
                    fullName = firebaseUser.displayName ?: "Pengguna",
                    email = firebaseUser.email ?: ""
                )

                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(newUser)
                    .await()

                newUser
            }

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            authService.resetPassword(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfile(fullName: String, profileImageUrl: String = ""): Result<User> {
        return try {
            val firebaseUser = authService.getCurrentUser()
                ?: return Result.failure(Exception("User tidak ditemukan"))

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build()

            firebaseUser.updateProfile(profileUpdates).await()

            val existingSnapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val finalImageUrl = if (profileImageUrl.isNotBlank()) {
                profileImageUrl
            } else {
                existingSnapshot.getString("profileImageUrl") ?: ""
            }

            val existingBase64 = existingSnapshot.getString("profileImageBase64") ?: ""

            val updatedUser = User(
                uid = firebaseUser.uid,
                fullName = fullName,
                email = firebaseUser.email ?: "",
                profileImageUrl = finalImageUrl,
                profileImageBase64 = existingBase64
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(updatedUser)
                .await()

            Result.success(updatedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfileWithBase64(
        fullName: String,
        imageBytes: ByteArray? = null
    ): Result<User> {
        return try {
            val firebaseUser = authService.getCurrentUser()
                ?: return Result.failure(Exception("User tidak ditemukan"))

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build()

            firebaseUser.updateProfile(profileUpdates).await()

            val existingSnapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val existingUrl = existingSnapshot.getString("profileImageUrl") ?: ""

            val base64String = if (imageBytes != null) {
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                val compressed = compressBitmap(bitmap)
                Base64.encodeToString(compressed, Base64.DEFAULT)
            } else {
                existingSnapshot.getString("profileImageBase64") ?: ""
            }

            val updatedUser = User(
                uid = firebaseUser.uid,
                fullName = fullName,
                email = firebaseUser.email ?: "",
                profileImageUrl = existingUrl,
                profileImageBase64 = base64String
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(updatedUser)
                .await()

            Result.success(updatedUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun compressBitmap(original: Bitmap): ByteArray {
        val maxSize = 300
        val ratio = minOf(
            maxSize.toFloat() / original.width,
            maxSize.toFloat() / original.height
        )

        val newWidth = (original.width * ratio).toInt()
        val newHeight = (original.height * ratio).toInt()

        val resized = Bitmap.createScaledBitmap(
            original,
            newWidth,
            newHeight,
            true
        )

        val outputStream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

        return outputStream.toByteArray()
    }

    suspend fun updatePassword(currentPassword: String, newPassword: String): Result<Unit> {
        return try {
            val firebaseUser = authService.getCurrentUser()
                ?: return Result.failure(Exception("User tidak ditemukan"))

            val credential = EmailAuthProvider.getCredential(
                firebaseUser.email ?: "",
                currentPassword
            )

            firebaseUser.reauthenticate(credential).await()
            firebaseUser.updatePassword(newPassword).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAccount(): Result<Unit> {
        return try {
            val firebaseUser = authService.getCurrentUser()
                ?: return Result.failure(Exception("User tidak ditemukan"))

            firestore.collection("users")
                .document(firebaseUser.uid)
                .delete()
                .await()

            firebaseUser.delete().await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        authService.logout()
    }
}