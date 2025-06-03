package com.example.meowspace.data

import android.content.Context
import com.example.meowspace.model.AuthResponse
import com.example.meowspace.model.Cat
import com.example.meowspace.model.LoginRequest
import com.example.meowspace.model.RegisterRequest
import com.example.meowspace.model.StatusResponse
import com.example.meowspace.model.UploadResponse
import com.example.meowspace.model.UserProfileResponse
import com.example.meowspace.service.TokenManager
import com.example.meowspace.service.UserService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File


class UserRepository(
    private val api: UserService,
    private val tokenManager: TokenManager,
    private val context: Context
) {
    suspend fun register(request: RegisterRequest): Response<AuthResponse>{
        return api.register(request)
    }

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

    suspend fun getUserProfile(): Response<UserProfileResponse> {
        val token = tokenManager.getToken()
            ?: throw IllegalStateException("Token is null")

        return api.getProfile("Bearer $token")
    }

    suspend fun postStatus(content: String, photoFile: File?): Response<StatusResponse> {
        val token = TokenManager(context).getToken()
            ?: throw IllegalStateException("Token is null")
        val contentPart = content.toRequestBody("text/plain".toMediaType())
        val photoPart = photoFile?.let {
            val requestFile = it.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("photo", it.name, requestFile)
        }
        return api.postStatus(
            token = "Bearer $token",
            content = contentPart,
            photo = photoPart
        )
    }

    suspend fun getStatuses(): Response<List<StatusResponse>> {
        val token = TokenManager(context).getToken()
            ?: throw IllegalStateException("Token is null")

        return api.getStatuses("Bearer $token")
    }

    suspend fun getCats(): List<Cat> {
        val token = TokenManager(context).getToken()
            ?: throw IllegalStateException("Token is null")

        return api.getCats("Bearer $token")
    }

    suspend fun uploadCat(
        photo: File,
        name: String,
        username: String,
        birthDate: String,
        gender: String,
        breed: String
    ): Response<UploadResponse> {
        val token = TokenManager(context).getToken()
            ?: throw IllegalStateException("Token is null")
        val requestFile = photo.asRequestBody("image/*".toMediaType())
        val photoPart = MultipartBody.Part.createFormData("photo", photo.name, requestFile)

        return api.uploadCat(
            token = "Bearer $token",
            photo = photoPart,
            name = name.toRequestBody("text/plain".toMediaType()),
            username = username.toRequestBody("text/plain".toMediaType()),
            birthDate = birthDate.toRequestBody("text/plain".toMediaType()),
            gender = gender.toRequestBody("text/plain".toMediaType()),
            breed = breed.toRequestBody("text/plain".toMediaType())
        )
    }
}