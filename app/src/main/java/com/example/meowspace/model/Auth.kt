package com.example.meowspace.model

// RegisterRequest.kt
data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)

// LoginRequest.kt
data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val message: String,
    val user: RegisteredUser
)

data class RegisteredUser(
    val fullName: String,
    val email: String
)
