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

class NitRepositoryTest {

    private val api: NitApiService = mockk()
    private val repo = NitRepository(api)

    @Test
    fun `login success returns keypass`() = runTest {
        val authRequest = AuthRequest("Test", "12345678")
        val response = AuthResponse("photography")
        coEvery { api.loginUser(authRequest) } returns response

        val result = repo.login("Test", "12345678")
        assertEquals(true, result.isSuccess)
        assertEquals("photography", result.getOrNull())
    }

    @Test
    fun `getDashboard success returns list`() = runTest {
        val entities = listOf(Entity(technique = "T1", equipment = "E1"))
        val resp = DashboardResponse(entities, entities.size)
        coEvery { api.getDashboard("photography") } returns resp

        val result = repo.getDashboard("photography")
        assertEquals(true, result.isSuccess)
        assertEquals(entities, result.getOrNull())
    }
}
