package com.example.s8081735assignment2.data.model

import com.squareup.moshi.Json

data class AuthRequest(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String
)

