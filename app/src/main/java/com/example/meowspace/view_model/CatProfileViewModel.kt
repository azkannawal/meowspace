package com.example.meowspace.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meowspace.data.UserRepository
import com.example.meowspace.model.Cat
import kotlinx.coroutines.launch

class CatProfileViewModel(
    private val repository: UserRepository
): ViewModel() {

        private val _catResult = MutableLiveData<List<Cat>?>()
        val catResult: LiveData<List<Cat>?> = _catResult

        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading: LiveData<Boolean> = _isLoading

        fun loadCatProfile() {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val cats = repository.getCats()
                    _catResult.value = cats
                    Log.d("CatViewModel", "Cats: $cats")
                } catch (e: Exception) {
                    Log.e("CatViewModel", "Exception: ${e.message}")
                } finally {
                    _isLoading.value = false
                }
            }
        }
}