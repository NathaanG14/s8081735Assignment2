package com.example.s8081735assignment2.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.s8081735assignment2.data.repository.NitRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Tests for LoginViewModel
// Make sure login works correctly and updates LiveData when login succeeds or fails
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    // Run LiveData instantly
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Use test dispatcher for deterministic coroutine execution
    private val testDispatcher = StandardTestDispatcher()

    private val repository: NitRepository = mockk()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        // Keeps logs cleaner during tests
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "ERROR")

        // Make the Main dispatcher deterministic for tests
        Dispatchers.setMain(testDispatcher)

        // Create viewmodel with mocked repository
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Test that checks successful login.
    // ViewModel should return a success result with the keypass "photography"
    @Test
    fun `login success updates loginResult and toggles loading`() = runTest {
        val username = "Test"
        val password = "12345678"

        // Arrange repository to return success
        coEvery { repository.login(username, password) } returns Result.success("photography")

        // Act
        viewModel.login(username, password)

        // Advance until all coroutines finish
        testScheduler.advanceUntilIdle()

        // Should post success with keypass and stop loading
        val result = viewModel.loginResult.value
        assertEquals(true, result?.isSuccess)
        assertEquals("photography", result?.getOrNull())
        assertEquals(false, viewModel.isLoading.value)
    }

    // Test that checks failed login.
    // Should post a failure message and turn off the loading spinner
    @Test
    fun `login failure updates loginResult with failure`() = runTest {
        val username = "Test"
        val password = "00000000"
        coEvery { repository.login(username, password) } returns Result.failure(Exception("Invalid credentials"))

        viewModel.login(username, password)
        testScheduler.advanceUntilIdle()

        // Posts an error message and stops loading
        val result = viewModel.loginResult.value
        assertEquals(true, result?.isFailure)
        assertEquals("Invalid credentials", result?.exceptionOrNull()?.message)
        assertEquals(false, viewModel.isLoading.value)
    }
}
