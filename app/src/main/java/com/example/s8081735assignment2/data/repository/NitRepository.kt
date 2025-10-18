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
        } catch (e: retrofit2.HttpException) {
            // Handle specific HTTP error codes
            val errorMsg = when (e.code()) {
                401 -> "Invalid credentials. Please try again."
                404 -> "User not found. Check your name or student ID."
                else -> "Login failed. Server returned ${e.code()}."
            }
            Result.failure(Exception(errorMsg))
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message ?: "Please try again."}"))
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


