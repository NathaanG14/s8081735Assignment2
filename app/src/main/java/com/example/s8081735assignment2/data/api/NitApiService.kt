package com.example.s8081735assignment2.data.api

import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.model.AuthResponse
import com.example.s8081735assignment2.data.model.DashboardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NitApiService {
    @POST("footscray/auth")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse
}

