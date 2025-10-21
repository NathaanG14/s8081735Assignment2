package com.example.s8081735assignment2.data.api

import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.model.AuthResponse
import com.example.s8081735assignment2.data.model.DashboardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Defines the API endpoints for the app using Retrofit.
// Each function maps to a backend HTTP request.
interface NitApiService {

    // Login endpoint.
    // Sends username and password to backend and returns a keypass if successful.
    @POST("footscray/auth")
    suspend fun loginUser(@Body request: AuthRequest): AuthResponse

    // Dashboard endpoint.
    // Retrieves photography entities using the provided keypass.
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse
}



