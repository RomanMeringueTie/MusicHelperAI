package com.example.maps.domain

import com.example.maps.data.model.ListenFull
import com.example.maps.data.repository.ListensRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetListensUseCaseTest {

    private lateinit var useCase: GetListensUseCase
    private lateinit var mockRepository: ListensRepository

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = GetListensUseCaseImpl(mockRepository)
    }

    @Test
    fun `invoke returns success with listens when repository succeeds`() = runTest {
        // Given
        val expectedListens = listOf(
            ListenFull(
                title = "Test Track",
                artist = "Test Artist",
                playedAt = 1704110400000L
            )
        )
        coEvery { mockRepository.getAll() } returns expectedListens

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedListens, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository throws exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Database error")
        coEvery { mockRepository.getAll() } throws expectedException

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `invoke returns empty list when repository returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<ListenFull>()
        coEvery { mockRepository.getAll() } returns emptyList

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList, result.getOrNull())
    }
}
