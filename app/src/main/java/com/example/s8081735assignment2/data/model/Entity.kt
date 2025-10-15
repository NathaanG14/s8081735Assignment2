package com.example.s8081735assignment2.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Entity(
    val property1: String?,
    val property2: String?,
    val description: String?
)

