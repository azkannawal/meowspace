package com.example.meowspace.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.data.AuthRepository
import com.example.meowspace.model.RegisterRequest
import com.example.meowspace.service.AuthService
import com.example.meowspace.service.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class RegisterViewModel : ViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var confirmPassword by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var confirmPasswordVisible by mutableStateOf(false)

    private val repo = AuthRepository(RetrofitInstance.authService)

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun register(onSuccess: () -> Unit) {
        println(">>> DEBUG kirim: name=$name, email=$email, password=$password")
        if (name.isBlank() || email.isBlank() || password.length < 8 || confirmPassword != password || !isValidEmail(email)) {
            errorMessage = when {
                name.isBlank() || email.isBlank() || password.isBlank() -> "Isi semua field dengan benar"
                !isValidEmail(email) -> "Format email tidak valid"
                password.length < 8 -> "Password minimal 8 karakter"
                password != confirmPassword -> "Password tidak cocok"
                else -> "Terjadi kesalahan"
            }
            return
        }


        viewModelScope.launch {
            try {
                val request = RegisterRequest(fullName = name, email = email, password = password)


                val response = RetrofitInstance.authService.register(request)

                if (response.code() == 201 && response.body() != null) {
                    val user = response.body()!!.user
                    onSuccess()
                    println("Akun berhasil dibuat: ${user.fullName}")
                } else {
                    errorMessage = response.errorBody()?.string() ?: "Registrasi gagal"
                }
            } catch (e: Exception) {
                errorMessage = "Gagal terhubung ke server"
            } finally {
                isLoading = false
            }
        }


    }
}