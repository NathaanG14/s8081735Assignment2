package com.example.s8081735assignment2.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.s8081735assignment2.data.model.Entity
import com.example.s8081735assignment2.data.repository.NitRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository: NitRepository = mockk()
    private lateinit var viewModel: DashboardViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadDashboard success posts entities and toggles loading`() = runTest {
        val keypass = "photography"
        val items = listOf(Entity(technique = "TestTech", equipment = "Cam"))
        coEvery { repository.getDashboard(keypass) } returns Result.success(items)

        viewModel.loadDashboard(keypass)
        advanceUntilIdle()

        val result = viewModel.entities.value
        assertEquals(true, result?.isSuccess)
        assertEquals(items, result?.getOrNull())
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `loadDashboard failure posts failure and toggles loading`() = runTest {
        val keypass = "photography"
        coEvery { repository.getDashboard(keypass) } returns Result.failure(Exception("Server error"))

        viewModel.loadDashboard(keypass)
        advanceUntilIdle()

        val result = viewModel.entities.value
        assertEquals(true, result?.isFailure)
        assertEquals("Server error", result?.exceptionOrNull()?.message)
        assertEquals(false, viewModel.isLoading.value)
    }
}
