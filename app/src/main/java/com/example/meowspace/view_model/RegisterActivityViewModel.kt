package com.example.meowspace.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.meowspace.repository.AuthRepository

class RegisterActivityViewModel(
    val authRepository: AuthRepository,
    val application: Application
) : ViewModel() {
}
