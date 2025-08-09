package com.example.maps.domain

import com.example.maps.data.datasource.InstalledAppsDataSource
import com.example.maps.data.model.AppInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetInstalledAppsUseCaseTest {

    private lateinit var useCase: GetInstalledAppsUseCase
    private lateinit var mockDataSource: InstalledAppsDataSource

    @Before
    fun setup() {
        mockDataSource = mockk()
        useCase = GetInstalledAppsUseCaseImpl(mockDataSource)
    }

    @Test
    fun `invoke returns success with apps when data source succeeds`() = runTest {
        // Given
        val expectedApps = listOf(
            AppInfo(
                packageName = "com.example.app1",
                appName = "Test App 1",
                icon = mockk(),
                isPicked = false
            ),
            AppInfo(
                packageName = "com.example.app2",
                appName = "Test App 2",
                icon = mockk(),
                isPicked = true
            )
        )
        coEvery { mockDataSource.get() } returns expectedApps

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedApps, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when data source throws exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Permission denied")
        coEvery { mockDataSource.get() } throws expectedException

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `invoke returns empty list when data source returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<AppInfo>()
        coEvery { mockDataSource.get() } returns emptyList

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList, result.getOrNull())
    }
}
