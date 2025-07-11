package com.example.maps.data.repository

import com.example.maps.data.db.Artist
import com.example.maps.data.db.ArtistDao
import com.example.maps.data.db.Listen
import com.example.maps.data.db.ListenDao
import com.example.maps.data.db.Track
import com.example.maps.data.db.TrackDao
import com.example.maps.data.model.ListenFull

class ListensRepositoryImpl(
    private val listenDao: ListenDao,
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao,
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

        val tenMinutesAgo = listen.playedAt - 10 * 60 * 1000

        val recentCount = listenDao.countRecentListens(trackId, tenMinutesAgo)

        if (recentCount == 0) {
            val listenEntity = Listen(trackId = trackId, playedAt = listen.playedAt)
            listenDao.insert(listenEntity)
        }
    }

    override suspend fun delete(listenFull: ListenFull) {
        val artistId = artistDao.getIdByName(listenFull.artist)
        val trackId =
            artistId?.let { trackDao.getIdByTitleAndArtist(listenFull.title, it) }
        val listenId =
            trackId?.let { listenDao.getIdByTrackAndPlayedAt(it, listenFull.playedAt) }

        listenId?.let {
            val listen = Listen(
                id = it,
                trackId = trackId,
                playedAt = listenFull.playedAt
            )
            listenDao.delete(listen)
        }
    }
}