package com.example.maps.domain

import com.example.maps.data.datasource.PermissionDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPermissionUseCaseTest {

    private lateinit var useCase: GetPermissionUseCase
    private lateinit var mockDataSource: PermissionDataSource

    @Before
    fun setup() {
        mockDataSource = mockk()
        useCase = GetPermissionUseCaseImpl(mockDataSource)
    }

    @Test
    fun `invoke returns true when data source returns true`() = runTest {
        // Given
        coEvery { mockDataSource.get() } returns true

        // When
        val result = useCase()

        // Then
        assertEquals(true, result)
    }

    @Test
    fun `invoke returns false when data source returns false`() = runTest {
        // Given
        coEvery { mockDataSource.get() } returns false

        // When
        val result = useCase()

        // Then
        assertEquals(false, result)
    }

    @Test
    fun `invoke calls data source get method`() = runTest {
        // Given
        coEvery { mockDataSource.get() } returns true

        // When
        useCase()

        // Then
        coEvery { mockDataSource.get() }
    }
}
