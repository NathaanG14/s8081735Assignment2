package com.example.s8081735assignment2.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

// Represents a photography entity.
// Parcelable for easy navigation between fragments.

@Parcelize
data class Entity(
    @Json(name = "technique") val technique: String? = null,
    @Json(name = "equipment") val equipment: String? = null,
    @Json(name = "subject") val subject: String? = null,
    @Json(name = "pioneeringPhotographer") val pioneeringPhotographer: String? = null,
    @Json(name = "yearIntroduced") val yearIntroduced: Int? = null,
    @Json(name = "description") val description: String? = null
) : Parcelable

