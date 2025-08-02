package com.example.maps.domain

import com.example.maps.data.db.ListenDao
import com.example.maps.data.model.TopArtist

class GetTopArtistsUseCaseImpl(private val listenDao: ListenDao) : GetTopArtistsUseCase {
    override suspend operator fun invoke(): Result<List<TopArtist>> {
        try {
            val result = listenDao.getTopArtists()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}