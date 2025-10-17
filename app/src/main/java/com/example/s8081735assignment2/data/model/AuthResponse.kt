package com.example.s8081735assignment2.data.model

import com.squareup.moshi.Json

data class AuthResponse(
    @Json(name = "keypass") val keypass: String
)

