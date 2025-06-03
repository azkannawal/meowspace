package com.example.meowspace.model

import com.google.gson.annotations.SerializedName

data class CreateStatusRequest(
    val content: String
)

data class UserData(
    val id: Int,
    val fullName: String,
    val email: String,
    @SerializedName("Cats")
    val cats: List<CatData>? = null
)


data class StatusResponse(
    val id: Int,
    val content: String,
    val userId: Int,
    val photoUrl: String?,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("User")
    val user: UserData?,
)

data class CatData(
    val id: Int,
    val name: String,
    val username: String,
    val birthDate: String,
    val photoUrl: String,
    val gender: String,
    val breed: String
)
