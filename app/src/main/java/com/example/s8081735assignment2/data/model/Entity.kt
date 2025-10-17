package com.example.s8081735assignment2.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Entity(
    @Json(name = "technique") val technique: String?,
    @Json(name = "equipment") val equipment: String?,
    @Json(name = "subject") val subject: String?,
    @Json(name = "pioneeringPhotographer") val pioneeringPhotographer: String?,
    @Json(name = "yearIntroduced") val yearIntroduced: Int?,
    @Json(name = "description") val description: String?
) : Parcelable

