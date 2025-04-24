package com.example.meowspace.service

import com.example.meowspace.model.AuthResponse
import com.example.meowspace.model.LoginRequest
import com.example.meowspace.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}