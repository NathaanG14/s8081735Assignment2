package com.example.s8081735assignment2.util

// Sealed class to represent API/Network state consistently.
sealed class Resource<out T> {
    object Idle : Resource<Nothing>() // No operation.
    object Loading : Resource<Nothing>() // API call in progress.
    data class Success<T>(val data: T) : Resource<T>() // API returned successful data.
    data class Error(val message: String) : Resource<Nothing>() // API returned error.
}

