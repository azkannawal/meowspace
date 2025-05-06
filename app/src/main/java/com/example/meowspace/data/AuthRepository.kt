package com.example.meowspace.data

import com.example.meowspace.model.AuthResponse
import com.example.meowspace.model.LoginRequest
import com.example.meowspace.model.LoginResponse
import com.example.meowspace.model.RegisterRequest
import com.example.meowspace.service.AuthService
import com.example.meowspace.service.TokenManager
import retrofit2.Response

class AuthRepository(
    private val api: AuthService,
    private val tokenManager: TokenManager
) {
    suspend fun register(request: RegisterRequest): Response<AuthResponse> =
        api.register(request)

    suspend fun login(request: LoginRequest): Result<String> {
        return try {
            val response = api.login(request)
            if (response.isSuccessful) {
                val token = response.body()?.token ?: return Result.failure(Exception("No token found"))
                tokenManager.saveToken(token)
                Result.success(token)
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getToken(): String? = tokenManager.getToken()
}