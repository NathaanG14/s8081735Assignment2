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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Use test dispatcher for deterministic coroutine execution
    private val testDispatcher = StandardTestDispatcher()

    private val repository: NitRepository = mockk()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        // Reduce SLF4J noise (if slf4j-simple is present it will respect this)
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

        // Assert loginResult success and loading false
        val result = viewModel.loginResult.value
        assertEquals(true, result?.isSuccess)
        assertEquals("photography", result?.getOrNull())
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `login failure updates loginResult with failure`() = runTest {
        val username = "Test"
        val password = "00000000"
        coEvery { repository.login(username, password) } returns Result.failure(Exception("Invalid credentials"))

        viewModel.login(username, password)
        testScheduler.advanceUntilIdle()

        val result = viewModel.loginResult.value
        assertEquals(true, result?.isFailure)
        assertEquals("Invalid credentials", result?.exceptionOrNull()?.message)
        assertEquals(false, viewModel.isLoading.value)
    }
}
