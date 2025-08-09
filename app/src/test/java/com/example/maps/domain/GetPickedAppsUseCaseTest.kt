package com.example.maps.domain

import com.example.maps.data.datasource.PickedAppsDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPickedAppsUseCaseTest {

    private lateinit var useCase: GetPickedAppsUseCase
    private lateinit var mockDataSource: PickedAppsDataSource

    @Before
    fun setup() {
        mockDataSource = mockk()
        useCase = GetPickedAppsUseCaseImpl(mockDataSource)
    }

    @Test
    fun `invoke returns success with picked apps when data source returns non-empty set`() = runTest {
        // Given
        val expectedApps = setOf("com.example.app1", "com.example.app2")
        coEvery { mockDataSource.get() } returns expectedApps

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedApps, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when data source returns empty set`() = runTest {
        // Given
        val emptySet = emptySet<String>()
        coEvery { mockDataSource.get() } returns emptySet

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals("No picked apps", result.exceptionOrNull()?.message)
    }

    @Test
    fun `invoke returns failure with correct exception type when data source returns empty set`() = runTest {
        // Given
        val emptySet = emptySet<String>()
        coEvery { mockDataSource.get() } returns emptySet

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)
    }
}
