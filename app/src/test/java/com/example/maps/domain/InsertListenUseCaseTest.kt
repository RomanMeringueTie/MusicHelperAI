package com.example.maps.domain

import com.example.maps.data.model.ListenFull
import com.example.maps.data.repository.ListensRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class InsertListenUseCaseTest {

    private lateinit var useCase: InsertListenUseCase
    private lateinit var mockRepository: ListensRepository

    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true)
        useCase = InsertListenUseCaseImpl(mockRepository)
    }

    @Test
    fun `invoke calls repository insert with correct listen`() = runTest {
        // Given
        val listen = ListenFull(
            title = "Test Track",
            artist = "Test Artist",
            playedAt = 1704110400000L
        )
        coEvery { mockRepository.insert(any()) } returns Unit

        // When
        useCase(listen)

        // Then
        coVerify { mockRepository.insert(listen) }
    }

    @Test
    fun `invoke handles repository insert successfully`() = runTest {
        // Given
        val listen = ListenFull(
            title = "Test Track",
            artist = "Test Artist",
            playedAt = 1704110400000L
        )
        coEvery { mockRepository.insert(any()) } returns Unit

        // When & Then
        useCase(listen) // Should not throw any exception
    }
}
