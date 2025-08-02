package com.example.maps.domain

import com.example.maps.data.db.ListenDao
import com.example.maps.data.model.TopTrack

class GetTopTracksUseCaseImpl(private val listenDao: ListenDao) : GetTopTracksUseCase {
    override suspend operator fun invoke(): Result<List<TopTrack>> {
        try {
            val result = listenDao.getTopTracks()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
} 