package com.example.s8081735assignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8081735assignment2.data.model.DashboardResponse
import com.example.s8081735assignment2.data.repository.NitRepository
import com.example.s8081735assignment2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: NitRepository
) : ViewModel() {

    private val _dashboardData = MutableStateFlow<Resource<DashboardResponse>>(Resource.Loading)
    val dashboardData: StateFlow<Resource<DashboardResponse>> = _dashboardData

    /**
     * Loads dashboard data for the provided keypass (e.g., "photography")
     */
    fun loadDashboardData(keypass: String = "photography") {
        viewModelScope.launch {
            _dashboardData.value = Resource.Loading
            try {
                val response = repository.getDashboardData(keypass)
                if (response.isSuccessful && response.body() != null) {
                    _dashboardData.value = Resource.Success(response.body()!!)
                } else {
                    _dashboardData.value =
                        Resource.Error("Failed to load data (Code: ${response.code()})")
                }
            } catch (e: Exception) {
                _dashboardData.value =
                    Resource.Error("Network error: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }
}


