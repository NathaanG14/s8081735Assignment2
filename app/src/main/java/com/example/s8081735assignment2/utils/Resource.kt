package com.example.s8081735assignment2.util

// A sealed class to represent API/network states cleanly
sealed class Resource<out T> {
    object Idle : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}

