package com.example.meowspace.view_model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.data.UserRepository
import com.example.meowspace.model.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var passwordVisible by mutableStateOf(false)

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun login(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank() || password.length < 8 || !isValidEmail(email)) {
            errorMessage = when {
                email.isBlank() || password.isBlank() -> "Isi semua field dengan benar"
                !isValidEmail(email) -> "Format email tidak valid"
                password.length < 8 -> "Password minimal 8 karakter"
                else -> "Terjadi kesalahan"
            }
            return
        }

        isLoading = true

        viewModelScope.launch {
            try {
                val request = LoginRequest(email = email, password = password)
                val result = repository.login(request)

                result.onSuccess {
                    println("Token: $it")
                    onSuccess()
                }.onFailure {
                    errorMessage = it.message ?: "Login gagal"
                }
            } catch (e: Exception) {
                errorMessage = "Login gagal: ${e.message ?: "Terjadi kesalahan"}"
            } finally {
                isLoading = false
            }
        }
    }
}

