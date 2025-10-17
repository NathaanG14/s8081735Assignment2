package com.example.s8081735assignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.model.AuthResponse
import com.example.s8081735assignment2.data.repository.NitRepository
import com.example.s8081735assignment2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: NitRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<AuthResponse>>(Resource.Idle)
    val loginState: StateFlow<Resource<AuthResponse>> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading
            try {
                val request = AuthRequest(username, password)
                val response = repository.loginUser(request)
                if (response.isSuccessful && response.body() != null) {
                    _loginState.value = Resource.Success(response.body()!!)
                } else {
                    _loginState.value = Resource.Error("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _loginState.value = Resource.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
}

