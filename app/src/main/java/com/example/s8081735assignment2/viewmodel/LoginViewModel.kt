package com.example.s8081735assignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.repository.NitRepository
import com.example.s8081735assignment2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: NitRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<String>>(Resource.Loading)
    val loginState: StateFlow<Resource<String>> = _loginState

    fun login(username: String, password: String) {
        _loginState.value = Resource.Loading
        viewModelScope.launch {
            try {
                val resp = repo.login(AuthRequest(username, password))
                val key = resp.keypass ?: ""
                if (key.isNotBlank()) {
                    _loginState.value = Resource.Success(key)
                } else {
                    _loginState.value = Resource.Error("Invalid credentials or empty keypass.")
                }
            } catch (e: Exception) {
                _loginState.value = Resource.Error("Login failed: ${e.localizedMessage ?: e.message}")
            }
        }
    }
}

