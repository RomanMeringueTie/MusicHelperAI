package com.example.maps.data.repository

import com.example.maps.data.datasource.ListensLocalDataSource
import com.example.maps.data.datasource.ListensRemoteDataSource
import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack
import com.example.maps.data.model.UserModel

class ListensRepositoryImpl(
    private val localDataSource: ListensLocalDataSource,
    private val remoteDataSource: ListensRemoteDataSource,
) : ListensRepository {
    override suspend fun getAll(): List<ListenFull> {
        return if (UserModel.isAuthorized && UserModel.userId != null) {
            remoteDataSource.getAll()
        } else {
            localDataSource.getAll()
        }
    }

    override suspend fun insert(listenFull: ListenFull) {
        if (UserModel.isAuthorized && UserModel.userId != null) {
            remoteDataSource.insert(listenFull)
        } else {
            localDataSource.insert(listenFull)
        }
    }

    override suspend fun delete(listenFull: ListenFull) {
        if (UserModel.isAuthorized && UserModel.userId != null) {
            remoteDataSource.delete(listenFull)
        } else {
            localDataSource.delete(listenFull)
        }
    }

    override suspend fun getTopArtists(): List<TopArtist> {
        return if (UserModel.isAuthorized && UserModel.userId != null) {
            remoteDataSource.getTopArtists()
        } else
            localDataSource.getTopArtists()
    }

    override suspend fun getTopTracks(): List<TopTrack> {
        return if (UserModel.isAuthorized && UserModel.userId != null) {
            remoteDataSource.getTopTracks()
        } else
            localDataSource.getTopTracks()
    }

}