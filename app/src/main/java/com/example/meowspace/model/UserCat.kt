package com.example.meowspace.model

data class UploadResponse(
    val message: String,
    val cat: Cat
)

data class Cat(
    val id: Int,
    val name: String,
    val username: String,
    val birthDate: String,
    val gender: String,
    val breed: String,
    val photoUrl: String,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String
)