package com.example.meowspace.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.data.UserRepository
import com.example.meowspace.model.UploadResponse
import kotlinx.coroutines.launch
import java.io.File


class PostCatProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uploadResult = MutableLiveData<Result<UploadResponse>>()
    val uploadResult: LiveData<Result<UploadResponse>> = _uploadResult

    val name = MutableLiveData("")
    val username = MutableLiveData("")
    val birthDate = MutableLiveData("")
    val gender = MutableLiveData("male")
    val breed = MutableLiveData("")
    val photoFile = MutableLiveData<File?>(null)

    fun uploadCat() {
        val file = photoFile.value ?: return
        val nameValue = name.value ?: ""
        val usernameValue = username.value ?: ""
        val birthDateValue = birthDate.value ?: ""
        val genderValue = gender.value ?: ""
        val breedValue = breed.value ?: ""

        Log.d("UploadDebug", "Name: $nameValue")
        Log.d("UploadDebug", "Username: $usernameValue")
        Log.d("UploadDebug", "Birth Date: $birthDateValue")
        Log.d("UploadDebug", "Gender: $genderValue")
        Log.d("UploadDebug", "Breed: $breedValue")
        Log.d("UploadDebug", "Photo exists: ${file.exists()}, size: ${file.length()}")

        viewModelScope.launch {
            try {
                val response = repository.uploadCat(
                    photo = file,
                    name = nameValue,
                    username = usernameValue,
                    birthDate = birthDateValue,
                    gender = genderValue,
                    breed = breedValue
                )
                if (response.isSuccessful) {
                    _uploadResult.postValue(Result.success(response.body()!!))
                } else {
                    val errorBody = response.errorBody()?.string()
                    _uploadResult.postValue(Result.failure(Exception("Upload gagal: ${response.message()}")))
                }
            } catch (e: Exception) {
                _uploadResult.postValue(Result.failure(e))
            }
        }
    }
}