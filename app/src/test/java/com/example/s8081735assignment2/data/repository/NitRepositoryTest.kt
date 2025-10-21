package com.example.s8081735assignment2.data.repository

import com.example.s8081735assignment2.data.api.NitApiService
import com.example.s8081735assignment2.data.model.AuthRequest
import com.example.s8081735assignment2.data.model.AuthResponse
import com.example.s8081735assignment2.data.model.DashboardResponse
import com.example.s8081735assignment2.data.model.Entity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

// Unit tests for NitRepository
// Makes sure login and dashboard request return the correct data when given valid credentials
class NitRepositoryTest {

    // Mocking the API so that we don't hit the real network during the test
    private val api: NitApiService = mockk()

    // Create a repository with the mocked API
    private val repo = NitRepository(api)

    // Checks that login returns the correct keypass when given valid credentials
    @Test
    fun `login success returns keypass`() = runTest {
        val authRequest = AuthRequest("Test", "12345678")
        val response = AuthResponse("photography")
        coEvery { api.loginUser(authRequest) } returns response

        // The result should be successful and return the keypass "Photography"
        val result = repo.login("Test", "12345678")
        assertEquals(true, result.isSuccess)
        assertEquals("photography", result.getOrNull())
    }

    // Checks that getDashboard returns the correct list of entities when given a valid keypass
    @Test
    fun `getDashboard success returns list`() = runTest {
        val entities = listOf(Entity(technique = "T1", equipment = "E1"))
        val resp = DashboardResponse(entities, entities.size)
        coEvery { api.getDashboard("photography") } returns resp

        // Result should be successful and return the list of entities
        val result = repo.getDashboard("photography")
        assertEquals(true, result.isSuccess)
        assertEquals(entities, result.getOrNull())
    }
}
