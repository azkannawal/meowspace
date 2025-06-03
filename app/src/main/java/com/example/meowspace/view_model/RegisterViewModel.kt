package com.example.meowspace.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.model.RegisterRequest
import com.example.meowspace.service.RetrofitInstance
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel : ViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var confirmPassword by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var confirmPasswordVisible by mutableStateOf(false)

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun register(onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || password.length < 8 || confirmPassword != password || !isValidEmail(email)) {
            errorMessage = when {
                name.isBlank() || email.isBlank() || password.isBlank() -> "Please fill in all fields correctly"
                !isValidEmail(email) -> "Invalid email format"
                password.length < 8 -> "Password must be at least 8 characters"
                password != confirmPassword -> "Passwords do not match"
                else -> "An error occurred"
            }
            return
        }


        viewModelScope.launch {
            try {
                val request = RegisterRequest(fullName = name, email = email, password = password)
                val response = RetrofitInstance.userService.register(request)

                if (response.code() == 201 && response.body() != null) {
                    val user = response.body()!!.user
                    onSuccess()
                    println("Akun berhasil dibuat: ${user.fullName}")
                } else {
                    val errorJson = response.errorBody()?.string()
                    val message = if (errorJson != null) JSONObject(errorJson).optString("message") else "Registrasi gagal"
                    errorMessage = message
                }
            } catch (e: Exception) {
                errorMessage = "Gagal terhubung ke server"
            } finally {
                isLoading = false
            }
        }


    }
}