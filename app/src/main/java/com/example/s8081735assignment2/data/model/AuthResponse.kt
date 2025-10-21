package com.example.s8081735assignment2.data.model

import com.squareup.moshi.Json

// Response from login API
// Contains the keypass token used for subsequent requests.
data class AuthResponse(
    @Json(name = "keypass") val keypass: String
)

