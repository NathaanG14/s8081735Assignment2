package com.example.s8081735assignment2.repository

import com.example.s8081735assignment2.data.api.NitApiService
import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.model.AuthResponse
import com.example.s8081735assignment2.data.model.DashboardResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NitRepository @Inject constructor(
    private val api: NitApiService
) {
    suspend fun login(username: String, password: String): AuthResponse {
        return api.login(AuthRequest(username, password))
    }

    suspend fun getDashboard(keypass: String): DashboardResponse {
        return api.getDashboard(keypass)
    }
}
