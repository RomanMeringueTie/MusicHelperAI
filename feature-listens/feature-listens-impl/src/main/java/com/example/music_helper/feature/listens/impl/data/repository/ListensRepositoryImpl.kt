package com.example.music_helper.feature.listens.impl.data.repository

import com.example.music_helper.common.api.model.UserSingleton
import com.example.music_helper.feature.listens.api.data.repository.ListensRepository
import com.example.music_helper.feature.listens.api.model.ListenFull
import com.example.music_helper.feature.listens.api.model.TopArtist
import com.example.music_helper.feature.listens.api.model.TopTrack
import com.example.music_helper.feature.listens.impl.data.datasource.ListensLocalDataSource
import com.example.music_helper.feature.listens.impl.data.datasource.ListensRemoteDataSource

class ListensRepositoryImpl(
    private val localDataSource: ListensLocalDataSource,
    private val remoteDataSource: ListensRemoteDataSource,
) : ListensRepository {
    override suspend fun getAll(): List<ListenFull> {
        return if (UserSingleton.isAuthorized && UserSingleton.userId != null) {
            remoteDataSource.getAll()
        } else {
            localDataSource.getAll()
        }
    }

    override suspend fun insert(listenFull: ListenFull) {
        if (UserSingleton.isAuthorized && UserSingleton.userId != null) {
            remoteDataSource.insert(listenFull)
        } else {
            localDataSource.insert(listenFull)
        }
    }

    override suspend fun delete(listenFull: ListenFull) {
        if (UserSingleton.isAuthorized && UserSingleton.userId != null) {
            remoteDataSource.delete(listenFull)
        } else {
            localDataSource.delete(listenFull)
        }
    }

    override suspend fun getTopArtists(): List<TopArtist> {
        return if (UserSingleton.isAuthorized && UserSingleton.userId != null) {
            remoteDataSource.getTopArtists()
        } else
            localDataSource.getTopArtists()
    }

    override suspend fun getTopTracks(): List<TopTrack> {
        return if (UserSingleton.isAuthorized && UserSingleton.userId != null) {
            remoteDataSource.getTopTracks()
        } else
            localDataSource.getTopTracks()
    }

}
