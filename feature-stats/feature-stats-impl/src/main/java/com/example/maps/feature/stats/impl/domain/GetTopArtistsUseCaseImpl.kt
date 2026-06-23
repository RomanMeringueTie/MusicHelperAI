package com.example.maps.feature.stats.impl.domain

import com.example.maps.feature.stats.api.domain.GetTopArtistsUseCase
import com.example.maps.feature.listens.api.model.TopArtist
import com.example.maps.feature.listens.api.data.repository.ListensRepository

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
