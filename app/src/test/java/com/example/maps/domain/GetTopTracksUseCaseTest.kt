package com.example.maps.domain

import com.example.maps.data.model.TopTrack
import com.example.maps.data.repository.ListensRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetTopTracksUseCaseTest {

    private lateinit var useCase: GetTopTracksUseCase
    private lateinit var mockRepository: ListensRepository

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = GetTopTracksUseCaseImpl(mockRepository)
    }

    @Test
    fun `invoke returns success with top tracks when repository succeeds`() = runTest {
        // Given
        val expectedTracks = listOf(
            TopTrack(
                trackName = "Test Track 1",
                artistName = "Test Artist 1",
                listenCount = 15
            ),
            TopTrack(
                trackName = "Test Track 2",
                artistName = "Test Artist 2",
                listenCount = 8
            )
        )
        coEvery { mockRepository.getTopTracks() } returns expectedTracks

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedTracks, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository throws exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Database error")
        coEvery { mockRepository.getTopTracks() } throws expectedException

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `invoke returns empty list when repository returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<TopTrack>()
        coEvery { mockRepository.getTopTracks() } returns emptyList

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList, result.getOrNull())
    }
}
