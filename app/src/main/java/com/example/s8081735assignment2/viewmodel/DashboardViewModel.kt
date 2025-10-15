package com.example.s8081735assignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8081735assignment2.data.model.Entity
import com.example.s8081735assignment2.repository.NitRepository
import com.example.s8081735assignment2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: NitRepository
) : ViewModel() {

    private val _entitiesState = MutableStateFlow<Resource<List<Entity>>>(Resource.Loading)
    val entitiesState: StateFlow<Resource<List<Entity>>> = _entitiesState

    fun loadEntities(keypass: String) {
        _entitiesState.value = Resource.Loading
        viewModelScope.launch {
            try {
                val resp = repo.getDashboard(keypass)
                _entitiesState.value = Resource.Success(resp.entities)
            } catch (e: Exception) {
                _entitiesState.value = Resource.Error(e.localizedMessage ?: "Failed to load")
            }
        }
    }
}
