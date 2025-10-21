package com.example.s8081735assignment2.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8081735assignment2.data.model.Entity
import com.example.s8081735assignment2.data.repository.NitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for DashboardFragment.
// Loads dashboard data and exposes LiveData for the UI
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: NitRepository
) : ViewModel() {

    private val _entities = MutableLiveData<Result<List<Entity>>>()
    val entities: LiveData<Result<List<Entity>>> = _entities

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    // Loads dashboard entities asynchronously.
    // Updates loading state and result LiveData.
    fun loadDashboard(keypass: String = "photography") {
        viewModelScope.launch {
            _isLoading.value = true
            _entities.value = repository.getDashboard(keypass)
            _isLoading.value = false
        }
    }
}



