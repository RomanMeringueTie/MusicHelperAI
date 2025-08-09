package com.example.maps.domain

import com.example.maps.data.model.TopArtist
import com.example.maps.data.repository.ListensRepository

class GetTopArtistsUseCaseImpl(private val listensRepository: ListensRepository) :
    GetTopArtistsUseCase {
    override suspend operator fun invoke(): Result<List<TopArtist>> {
        try {
            val result = listensRepository.getTopArtists()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}