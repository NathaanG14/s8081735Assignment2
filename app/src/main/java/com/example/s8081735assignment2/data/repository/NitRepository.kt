package com.example.s8081735assignment2.data.repository

import com.example.s8081735assignment2.data.api.NitApiService
import com.example.s8081735assignment2.data.model.AuthRequest
import javax.inject.Inject

class NitRepository @Inject constructor(
    private val api: NitApiService
) {
    suspend fun loginUser(request: AuthRequest) = api.loginUser(request)

    suspend fun getDashboardData(keypass: String) = api.getDashboardData(keypass)
}


