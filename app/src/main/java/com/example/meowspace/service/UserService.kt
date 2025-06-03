package com.example.meowspace.service

import com.example.meowspace.model.AuthResponse
import com.example.meowspace.model.Cat
import com.example.meowspace.model.LoginRequest
import com.example.meowspace.model.LoginResponse
import com.example.meowspace.model.MidtransRequest
import com.example.meowspace.model.MidtransResponse
import com.example.meowspace.model.RegisterRequest
import com.example.meowspace.model.StatusResponse
import com.example.meowspace.model.UploadResponse
import com.example.meowspace.model.UserProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserService {
    @POST("/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("/users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/users/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<UserProfileResponse>

    @Multipart
    @POST("users/statuses")
    suspend fun postStatus(
        @Header("Authorization") token: String,
        @Part("content") content: RequestBody,
        @Part photo: MultipartBody.Part? = null
    ): Response<StatusResponse>

    @GET("users/statuses")
    suspend fun getStatuses(
        @Header("Authorization") token: String
    ): Response<List<StatusResponse>>

    @GET("users/catprofile")
    suspend fun getCats(
        @Header("Authorization") token: String
    ): List<Cat>

    @Multipart
    @POST("users/catprofile")
    suspend fun uploadCat(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("username") username: RequestBody,
        @Part("birthDate") birthDate: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("breed") breed: RequestBody
    ): Response<UploadResponse>

    @POST("payments/create-transaction")
    suspend fun createTransaction(
        @Body request: MidtransRequest
    ): MidtransResponse
}