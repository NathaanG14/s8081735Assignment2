# s8081735Assignment2 — Photography App (NIT3213 Mobile Application Development)

This project is my Assignment 2 for NIT3213, developed using **Kotlin**, **Android Studio**, **Retrofit**, and **Hilt**.  
It’s a small mobile app that allows users to log in and view a photography dashboard containing various techniques, equipment, and other related entities retrieved from an online API.

---

## Overview

The app connects to a remote API hosted at: https://nit3213api.onrender.com/


It performs the following:
- **Login authentication** using a POST request.
- **Dashboard data retrieval** using a GET request.
- **Displays data in a RecyclerView** (e.g., technique, equipment, photographer, year, etc.).
- **Navigates to a Details screen** when an item is tapped.

---

## App Features

### Login Screen
- Users enter their **first name** and **student ID**.
- Makes a **POST** request to the endpoint: https://nit3213api.onrender.com/footscray/auth
- Successful login returns a `keypass` (always `"photography"` in this project).
- Invalid login shows error messages such as:
- “Invalid credentials. Please try again.”
- “User not found. Check your name or student ID.”
- On success, the user is navigated to the **Dashboard Screen**.

---

### Dashboard Screen
- Fetches photography data from the API.
- Makes a **GET** request from the `NitRepository.getDashboard()` method through the `NitApiService` interface.
- Endpoint: https://nit3213api.onrender.com/dashboard/photography
- Uses the keypass `"photography"` from the login result.
- Displays all entities from the response (techniques, equipment, subjects, pioneering photographers, etc.) in a **RecyclerView**.
- If no data is available, a message appears: *“No photography data available.”*
- Clicking on any item opens the **Details Screen**.

---

### Details Screen
- Shows detailed information about the selected entity.
- Displays fields such as `technique` and `description`.
- Data is passed between fragments using Android Navigation Component Safe Args.

---

## Unit Testing

Unit tests are included to verify critical parts of the app:

| **Test Class** | **Purpose** |
|----------------|-------------|
| `LoginViewModelTest` | Tests login success and failure handling. |
| `DashboardViewModelTest` | Tests dashboard data loading and error states. |
| `NitRepositoryTest` | Tests Retrofit integration and repository return values. |

Run the tests in **Android Studio** using:  
`Run > Run Tests in 'test'`

All tests should pass successfully.

---

## Build & Run Instructions

1. Clone this repository:
   ```bash
   git clone https://github.com/NathaanG14/s8081735Assignment2.git

---

## Tech Stack

- **Language:** Kotlin  
- **Architecture:** MVVM (Model–View–ViewModel)
- **Dependency Injection:** Hilt (Dagger)
- **Networking:** Retrofit + Moshi + OkHttp Logging
- **Navigation:** Android Jetpack Navigation Component
- **Coroutines:** Kotlin Coroutines for async tasks
- **Testing:** JUnit + MockK + Coroutine Test

---

## Project Structure
com.example.s8081735assignment2/
│
├── data/
│ ├── api/
│ │ └── NitApiService.kt # Retrofit API interface
│ ├── model/ # Data classes (Auth, Entity, etc.)
│ └── repository/
│ └── NitRepository.kt # Handles API calls
│
├── di/
│ └── AppModule.kt # Provides Hilt dependencies
│
├── ui/
│ ├── login/ # Login screen + ViewModel
│ ├── dashboard/ # Dashboard + Adapter + ViewModel
│ └── details/ # Details fragment
│
├── utils/
│ └── Resource.kt # Helper sealed class for state handling
│
├── MyApplication.kt # Hilt entry point
└── MainActivity.kt # Root activity with NavHostFragment

---

## Code Highlights

### Retrofit & Repository Layer

**NitApiService.kt**
```kotlin
@POST("footscray/auth")
suspend fun loginUser(@Body request: AuthRequest): AuthResponse

@GET("dashboard/{keypass}")
suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse

suspend fun login(username: String, password: String): Result<String> {
    return try {
        val response = api.loginUser(AuthRequest(username, password))
        Result.success(response.keypass)
    } catch (e: retrofit2.HttpException) {
        val errorMsg = when (e.code()) {
            401 -> "Invalid credentials. Please try again."
            404 -> "User not found. Check your name or student ID."
            else -> "Login failed. Server returned ${e.code()}."
        }
        Result.failure(Exception(errorMsg))
    } catch (e: Exception) {
        Result.failure(Exception("Network error: ${e.message ?: "Please try again."}"))
    }
}


