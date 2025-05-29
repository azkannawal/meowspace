package com.example.meowspace.model

import com.google.gson.annotations.SerializedName

data class CreateStatusRequest(
    val content: String
)

data class StatusResponse(
    val id: Int,
    val content: String,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("User")
    val user: UserData?
)

data class UserData(
    val id: Int,
    val fullName: String,
    val email: String
)