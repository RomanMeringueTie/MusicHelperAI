package com.example.maps.domain

import com.example.maps.data.model.TopArtist
import com.example.maps.data.repository.ListensRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

    class GetTopArtistsUseCaseTest {

    private lateinit var useCase: GetTopArtistsUseCase
    private lateinit var mockRepository: ListensRepository

    @Before
    fun setup() {
        mockRepository = mockk()
        useCase = GetTopArtistsUseCaseImpl(mockRepository)
    }

    @Test
    fun `invoke returns success with top artists when repository succeeds`() = runTest {
        // Given
        val expectedArtists = listOf(
            TopArtist(
                artistName = "Test Artist 1",
                trackCount = 10
            ),
            TopArtist(
                artistName = "Test Artist 2",
                trackCount = 5
            )
        )
        coEvery { mockRepository.getTopArtists() } returns expectedArtists

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedArtists, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository throws exception`() = runTest {
        // Given
        val expectedException = RuntimeException("Database error")
        coEvery { mockRepository.getTopArtists() } throws expectedException

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `invoke returns empty list when repository returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<TopArtist>()
        coEvery { mockRepository.getTopArtists() } returns emptyList

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList, result.getOrNull())
    }
}
