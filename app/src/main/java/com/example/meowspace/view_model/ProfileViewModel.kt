package com.example.meowspace.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.model.UserProfileResponse
import com.example.meowspace.service.RetrofitInstance
import com.example.meowspace.service.TokenManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class ProfileViewModel : ViewModel() {
    var userProfile by mutableStateOf<UserProfileResponse?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun loadProfile(context: Context) {
        viewModelScope.launch {
            try {
                val token = TokenManager(context).getToken()
                Log.d("ProfileViewModel", "Token: $token")
                if (!token.isNullOrEmpty()) {
                    val response = RetrofitInstance.authService.getProfile("Bearer $token")
                    Log.d("ProfileViewModel", "Response code: ${response.code()}")
                    if (response.isSuccessful) {
                        Log.d("ProfileViewModel", "Response body: ${response.body()}")
                        userProfile = response.body()
                    } else {
                        Log.e("ProfileViewModel", "Response failed with code: ${response.code()}")
                    }
                } else {
                    Log.e("ProfileViewModel", "Token is null or empty")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Exception: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

}

