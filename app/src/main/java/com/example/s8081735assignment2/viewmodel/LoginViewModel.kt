package com.example.s8081735assignment2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8081735assignment2.data.repository.NitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for LoginFragment..
// Handles login logic and exposes result LiveData and loading state.
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: NitRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    // Initiates login using username and password
    // Updates LiveData for success/failure and loading state.

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginResult.value = repository.login(username, password)
            _isLoading.value = false
        }
    }
}


