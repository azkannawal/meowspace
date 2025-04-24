package com.example.meowspace.data

import com.example.meowspace.model.AuthResponse
import com.example.meowspace.model.LoginRequest
import com.example.meowspace.model.RegisterRequest
import com.example.meowspace.service.AuthService
import retrofit2.Response

class AuthRepository(private val api: AuthService) {
    suspend fun register(request: RegisterRequest): Response<AuthResponse> =
        api.register(request)

    suspend fun login(request: LoginRequest): Response<AuthResponse> =
        api.login(request)
}