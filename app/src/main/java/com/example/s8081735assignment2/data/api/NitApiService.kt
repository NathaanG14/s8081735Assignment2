package com.example.s8081735assignment2.data.api

import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.model.AuthResponse
import com.example.s8081735assignment2.data.model.DashboardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NitApiService {

    // POST request for login (Footscray class location)
    @POST("footscray/auth")
    suspend fun loginUser(@Body request: AuthRequest): Response<AuthResponse>

    // GET request for dashboard using keypass (e.g., "photography")
    @GET("dashboard/{keypass}")
    suspend fun getDashboardData(@Path("keypass") keypass: String): Response<DashboardResponse>
}

