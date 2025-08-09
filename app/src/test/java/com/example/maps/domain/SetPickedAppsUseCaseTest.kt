package com.example.maps.domain

import com.example.maps.data.datasource.PickedAppsDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetPickedAppsUseCaseTest {

    private lateinit var useCase: SetPickedAppsUseCase
    private lateinit var mockDataSource: PickedAppsDataSource

    @Before
    fun setup() {
        mockDataSource = mockk(relaxed = true)
        useCase = SetPickedAppsUseCaseImpl(mockDataSource)
    }

    @Test
    fun `invoke calls data source set with correct picked apps`() = runTest {
        // Given
        val pickedApps = setOf("com.example.app1", "com.example.app2")
        coEvery { mockDataSource.set(any()) } returns Unit

        // When
        useCase(pickedApps)

        // Then
        coVerify { mockDataSource.set(pickedApps) }
    }

    @Test
    fun `invoke handles data source set successfully`() = runTest {
        // Given
        val pickedApps = setOf("com.example.app1", "com.example.app2")
        coEvery { mockDataSource.set(any()) } returns Unit

        // When & Then
        useCase(pickedApps) // Should not throw any exception
    }

    @Test
    fun `invoke handles empty set`() = runTest {
        // Given
        val emptySet = emptySet<String>()
        coEvery { mockDataSource.set(any()) } returns Unit

        // When & Then
        useCase(emptySet) // Should not throw any exception
    }
}
