package com.example.maps.domain

import com.example.maps.data.datasource.AIReviewDataSource
import com.example.maps.data.model.ListensReview
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetListensReviewUseCaseTest {

    private lateinit var useCase: GetListensReviewUseCase
    private lateinit var mockDataSource: AIReviewDataSource

    @Before
    fun setup() {
        mockDataSource = mockk()
        useCase = GetListensReviewUseCaseImpl(mockDataSource)
    }

    @Test
    fun `invoke returns success with review when data source succeeds`() = runTest {
        // Given
        val listens = "Test listens data"
        val expectedListensReview = ListensReview(
            recommendations = "Great music taste! You listen to a variety of genres"
        )
        coEvery { mockDataSource.get(any()) } returns expectedListensReview

        // When
        val result = useCase(listens)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedListensReview, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when data source throws exception`() = runTest {
        // Given
        val listens = "Test listens data"
        val expectedException = RuntimeException("AI service error")
        coEvery { mockDataSource.get(any()) } throws expectedException

        // When
        val result = useCase(listens)

        // Then
        assertTrue(result.isFailure)
        assertEquals(expectedException, result.exceptionOrNull())
    }

    @Test
    fun `invoke calls data source with correct listens parameter`() = runTest {
        // Given
        val listens = "Test listens data"
        val expectedListensReview = ListensReview(
            recommendations = "Great music taste! You listen to a variety of genres"
        )
        coEvery { mockDataSource.get(any()) } returns expectedListensReview

        // When
        useCase(listens)

        // Then
        coEvery { mockDataSource.get(listens) }
    }
}
