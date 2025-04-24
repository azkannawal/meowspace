package com.example.meowspace.view_model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun login(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Email dan Password wajib diisi"
            return
        }

        isLoading = true
        errorMessage = ""

        CoroutineScope(Dispatchers.IO).launch {
            // Simulasi panggil API (ganti dengan retrofit nanti)
            kotlinx.coroutines.delay(1000)

            isLoading = false

            if (email == "test@email.com" && password == "123456") {
                onSuccess()
            } else {
                errorMessage = "Login gagal. Cek email dan password"
            }
        }
    }
}