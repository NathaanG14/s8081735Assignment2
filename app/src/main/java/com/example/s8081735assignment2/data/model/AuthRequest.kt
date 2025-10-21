package com.example.s8081735assignment2.data.model

import com.squareup.moshi.Json

// Request body for the login API.
// Maps JSON keys to Kotlin properties.
data class AuthRequest(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String
)

