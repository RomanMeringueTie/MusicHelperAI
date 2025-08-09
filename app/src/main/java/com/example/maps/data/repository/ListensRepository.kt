package com.example.maps.data.repository

import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack

interface ListensRepository {
    suspend fun getAll(): List<ListenFull>

    suspend fun insert(listenFull: ListenFull)

    suspend fun delete(listenFull: ListenFull)

    suspend fun getTopArtists(): List<TopArtist>

    suspend fun getTopTracks(): List<TopTrack>
}