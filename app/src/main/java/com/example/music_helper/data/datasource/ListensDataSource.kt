package com.example.music_helper.data.datasource

import com.example.music_helper.data.model.ListenFull
import com.example.music_helper.data.model.TopArtist
import com.example.music_helper.data.model.TopTrack

interface ListensDataSource {
    suspend fun getAll(): List<ListenFull>
    suspend fun insert(listen: ListenFull)
    suspend fun delete(listenFull: ListenFull)
    suspend fun getTopArtists(): List<TopArtist>
    suspend fun getTopTracks(): List<TopTrack>
}