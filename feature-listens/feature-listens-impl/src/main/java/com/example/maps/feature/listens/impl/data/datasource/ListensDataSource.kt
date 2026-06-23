package com.example.maps.feature.listens.impl.data.datasource

import com.example.maps.feature.listens.api.model.ListenFull
import com.example.maps.feature.listens.api.model.TopArtist
import com.example.maps.feature.listens.api.model.TopTrack

interface ListensDataSource {
    suspend fun getAll(): List<ListenFull>
    suspend fun insert(listen: ListenFull)
    suspend fun delete(listenFull: ListenFull)
    suspend fun getTopArtists(): List<TopArtist>
    suspend fun getTopTracks(): List<TopTrack>
}
