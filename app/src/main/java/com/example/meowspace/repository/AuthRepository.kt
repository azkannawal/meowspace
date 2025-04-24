package com.example.meowspace.repository

import com.example.meowspace.data.ValidateEmailBody
import com.example.meowspace.utils.APIConsumer
import com.example.meowspace.utils.RequestStatus
import com.example.meowspace.utils.SimplifiedMessage
import kotlinx.coroutines.flow.flow

class AuthRepository(private val consumer: APIConsumer) {
    fun validateEmailAddress(body: ValidateEmailBody) = flow {
        emit(RequestStatus.Waiting)
        try {
            val response = consumer.validateEmailAddress(body) // langsung objeknya
            emit(RequestStatus.Success(response))
        } catch (e: Exception) {
            // contoh error handling, sesuaikan sesuai bentuk error dari API kamu
            val errorMap = hashMapOf("network" to (e.message ?: "Unknown Error"))
            emit(RequestStatus.Error(errorMap))
        }
    }
}
