package com.example.s8081735assignment2.data.repository

import com.example.s8081735assignment2.data.api.NitApiService
import com.example.s8081735assignment2.data.model.AuthRequest
import javax.inject.Inject

class NitRepository @Inject constructor(
    private val api: NitApiService
) {
    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = api.loginUser(AuthRequest(username, password))
            Result.success(response.keypass)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDashboard(keypass: String): Result<List<com.example.s8081735assignment2.data.model.Entity>> {
        return try {
            val response = api.getDashboard(keypass)
            Result.success(response.entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


