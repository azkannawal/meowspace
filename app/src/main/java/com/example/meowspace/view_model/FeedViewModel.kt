package com.example.meowspace.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.data.UserRepository
import com.example.meowspace.model.StatusResponse
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _postResult = MutableLiveData<List<StatusResponse>?>()
    val postResult: LiveData<List<StatusResponse>?> = _postResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadFeed() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getStatuses()
                if (response.isSuccessful) {
                    _postResult.value = response.body()
                    val body = response.body()
                    Log.d("FeedViewModel", "Statuses: $body")
                } else {
                    Log.e("FeedViewModel", "Failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("FeedViewModel", "Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}


