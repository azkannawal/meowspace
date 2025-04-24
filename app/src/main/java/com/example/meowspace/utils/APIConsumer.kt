package com.example.meowspace.utils

import com.example.meowspace.data.UniqueEmailValidationResponse
import com.example.meowspace.data.ValidateEmailBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIConsumer {

    @POST(value = "users/validate-unique-email")
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): UniqueEmailValidationResponse

}
