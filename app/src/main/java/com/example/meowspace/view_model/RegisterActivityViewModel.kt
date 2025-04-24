package com.example.meowspace.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.data.User
import com.example.meowspace.data.ValidateEmailBody
import com.example.meowspace.repository.AuthRepository
import com.example.meowspace.utils.RequestStatus
import kotlinx.coroutines.launch

class RegisterActivityViewModel(
    val authRepository: AuthRepository,
    val application: Application
) : ViewModel() {

    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var isUniqueEmail: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var user: MutableLiveData<User> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getIsUniqueEmail(): LiveData<Boolean> = isUniqueEmail
    fun getUser(): LiveData<User> = user

    fun setErrorMessage(errors: HashMap<String, String>) {
        errorMessage.value = errors
    }
    fun validateEmailAddress(body: ValidateEmailBody, onResult: (Boolean) -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            authRepository.validateEmailAddress(body).collect { result ->
                isLoading.value = false
                when (result) {
                    is RequestStatus.Success -> {
                        isUniqueEmail.value = result.data.isUnique
                        onResult(result.data.isUnique)
                    }
                    is RequestStatus.Error -> {
                        setErrorMessage(result.message)
                        onResult(false)
                    }
                    else -> {}
                }
            }
        }
    }


}
