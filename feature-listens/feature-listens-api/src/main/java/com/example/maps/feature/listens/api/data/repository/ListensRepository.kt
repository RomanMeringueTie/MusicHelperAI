package com.example.maps.feature.listens.api.data.repository

import com.example.maps.feature.listens.api.model.ListenFull
import com.example.maps.feature.listens.api.model.TopArtist
import com.example.maps.feature.listens.api.model.TopTrack

interface ListensRepository {
    suspend fun getAll(): List<ListenFull>

    suspend fun insert(listenFull: ListenFull)

    suspend fun delete(listenFull: ListenFull)

    suspend fun getTopArtists(): List<TopArtist>

    suspend fun getTopTracks(): List<TopTrack>
}