package com.example.maps.data.repository

import com.example.maps.data.db.Artist
import com.example.maps.data.db.ArtistDao
import com.example.maps.data.db.Listen
import com.example.maps.data.db.ListenDao
import com.example.maps.data.model.ListenFull
import com.example.maps.data.db.Track
import com.example.maps.data.db.TrackDao

class ListensRepositoryImpl(
    private val listenDao: ListenDao,
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao
) : ListensRepository {
    override suspend fun getAll(): List<ListenFull> {
        return listenDao.getAll()
    }

    override suspend fun insert(listen: ListenFull) {
        val artistName = listen.artist
        val title = listen.title

        val artistId = artistDao.getIdByName(artistName)
            ?: artistDao.insert(Artist(name = artistName))

        val trackId = trackDao.getIdByTitleAndArtist(title, artistId)
            ?: trackDao.insert(Track(title = title, artistId = artistId))

        val listen = Listen(trackId = trackId)
        listenDao.insert(listen)
    }
}