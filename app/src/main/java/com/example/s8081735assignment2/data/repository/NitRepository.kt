package com.example.s8081735assignment2.data.repository

import com.example.s8081735assignment2.data.api.NitApiService
import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.model.AuthResponse
import com.example.s8081735assignment2.data.model.DashboardResponse
import javax.inject.Inject

class NitRepository @Inject constructor(private val api: NitApiService) {
    suspend fun login(request: AuthRequest): AuthResponse = api.login(request)
    suspend fun getDashboard(keypass: String): DashboardResponse = api.getDashboard(keypass)
}

