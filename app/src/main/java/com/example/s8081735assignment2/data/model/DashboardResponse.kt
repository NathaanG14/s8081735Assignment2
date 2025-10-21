package com.example.s8081735assignment2.data.model

import com.squareup.moshi.Json

// Response from dashboard API.
// Contains a list of entities and the total number of entities.

data class DashboardResponse(
    @Json(name = "entities") val entities: List<Entity>,
    @Json(name = "entityTotal") val entityTotal: Int
)
