package com.example.meowspace.model

data class MidtransResponse(val snapToken: String)

data class MidtransRequest(
    val orderId: String,
    val grossAmount: Int,
    val fullName: String,
    val email: String
)
