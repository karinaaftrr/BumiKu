package com.example.bumiku.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bumiku.R
import model.User

class AuthViewModel : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val userList = listOf(
        User("Tiwi Mustika Dewi", "tiwi@gmail.com", "123", R.drawable.tiwi),
        User("Karina Fitriamalia", "karina@gmail.com", "456", R.drawable.karina),
        User("Rama Praditha Ryananda", "rama@gmail.com", "789", R.drawable.rama),
        User("Putri Maharani", "rani@gmail.com", "321", R.drawable.rani)
    )

    fun login(emailInput: String, passwordInput: String): Boolean {
        isLoading = true
        errorMessage = null
        val email = emailInput.trim()
        val password = passwordInput.trim()

        val userFound = userList.find { it.email.equals(email, ignoreCase = true) && it.password == password }

        return if (userFound != null) {
            currentUser = userFound
            isLoading = false
            true
        } else {
            errorMessage = "Email atau Password salah!"
            isLoading = false
            false
        }
    }

    fun logout() {
        currentUser = null
    }
}
