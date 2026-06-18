package com.example.bumiku.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bumiku.data.model.User
import com.example.bumiku.data.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess

    init {
        if (repository.isLoggedIn()) {
            viewModelScope.launch {
                _currentUser.value = repository.getCurrentUser()
            }
        }
    }

    fun register(
        fullname: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.register(fullname, email, password)

            result.onSuccess {
                repository.logout()
                _currentUser.value = null
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message ?: "Registrasi gagal"
            }

            _isLoading.value = false
        }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.login(email, password)

            result.onSuccess { user ->
                _currentUser.value = user
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message ?: "Login gagal"
            }

            _isLoading.value = false
        }
    }

    fun signInWithGoogle(credential: AuthCredential, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.signInWithCredential(credential)

            result.onSuccess { user ->
                _currentUser.value = user
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message ?: "Gagal masuk dengan Google"
            }

            _isLoading.value = false
        }
    }

    fun signInWithGitHub(activity: Activity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.signInWithGitHub(activity)

            result.onSuccess { user ->
                _currentUser.value = user
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message ?: "Gagal masuk dengan GitHub"
            }

            _isLoading.value = false
        }
    }

    fun resetPassword(
        email: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.resetPassword(email)

            result.onSuccess { onSuccess() }
                .onFailure { _errorMessage.value = it.message ?: "Gagal mengirim reset password" }

            _isLoading.value = false
        }
    }

    fun updateProfile(
        fullName: String,
        profileImageUrl: String = "",
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _updateSuccess.value = false

            val result = repository.updateProfile(
                fullName = fullName,
                profileImageUrl = profileImageUrl
            )

            result.onSuccess { updatedUser ->
                _currentUser.value = updatedUser
                _updateSuccess.value = true
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message ?: "Gagal memperbarui profil"
            }

            _isLoading.value = false
        }
    }

    fun updatePassword(
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.updatePassword(currentPassword, newPassword)

            result.onSuccess { onSuccess() }
                .onFailure { _errorMessage.value = it.message ?: "Gagal memperbarui password" }

            _isLoading.value = false
        }
    }

    fun deleteAccount(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.deleteAccount()

            result.onSuccess {
                _currentUser.value = null
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message ?: "Gagal menghapus akun"
            }

            _isLoading.value = false
        }
    }

    fun logout() {
        repository.logout()
        _currentUser.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearUpdateSuccess() {
        _updateSuccess.value = false
    }

    fun isLoggedIn(): Boolean = repository.isLoggedIn()

    fun updateProfileWithBase64(
        fullName: String,
        imageBytes: ByteArray? = null,
        onSuccess: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _updateSuccess.value = false

            val result = repository.updateProfileWithBase64(
                fullName = fullName,
                imageBytes = imageBytes
            )

            result.onSuccess { updatedUser ->
                _currentUser.value = updatedUser
                _updateSuccess.value = true
                onSuccess()
            }.onFailure {
                _errorMessage.value = it.message ?: "Gagal memperbarui profil"
            }

            _isLoading.value = false
        }
    }
}