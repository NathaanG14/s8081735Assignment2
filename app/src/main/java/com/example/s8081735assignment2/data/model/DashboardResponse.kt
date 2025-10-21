package com.example.s8081735assignment2.data.model

import com.squareup.moshi.Json

data class DashboardResponse(
    @Json(name = "entities") val entities: List<Entity>,
    @Json(name = "entityTotal") val entityTotal: Int
)
