package com.example.maps.domain

import com.example.maps.data.datasource.NotificationSettingDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetNotificationSettingUseCaseTest {

    private lateinit var useCase: SetNotificationSettingUseCase
    private lateinit var mockDataSource: NotificationSettingDataSource

    @Before
    fun setup() {
        mockDataSource = mockk(relaxed = true)
        useCase = SetNotificationSettingUseCaseImpl(mockDataSource)
    }

    @Test
    fun `invoke calls data source set with true when isAllowed is true`() = runTest {
        // Given
        coEvery { mockDataSource.set(any()) } returns Unit

        // When
        useCase(true)

        // Then
        coVerify { mockDataSource.set(true) }
    }

    @Test
    fun `invoke calls data source set with false when isAllowed is false`() = runTest {
        // Given
        coEvery { mockDataSource.set(any()) } returns Unit

        // When
        useCase(false)

        // Then
        coVerify { mockDataSource.set(false) }
    }

    @Test
    fun `invoke handles data source set successfully`() = runTest {
        // Given
        coEvery { mockDataSource.set(any()) } returns Unit

        // When & Then
        useCase(true) // Should not throw any exception
    }
}
