package com.example.maps.data.datasource

import com.example.maps.data.db.Artist
import com.example.maps.data.db.ArtistDao
import com.example.maps.data.db.Listen
import com.example.maps.data.db.ListenDao
import com.example.maps.data.db.Track
import com.example.maps.data.db.TrackDao
import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack

class ListensLocalDataSourceImpl(
    private val listenDao: ListenDao,
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao,
) : ListensLocalDataSource {
    override suspend fun getAll(): List<ListenFull> {
        return listenDao.getAll()
    }

    override suspend fun insert(listen: ListenFull) {
        val artistName = listen.artist
        val title = listen.title
        var artistId = artistDao.getIdByName(artistName)
        if (artistId == null) {
            artistId = artistName.replace(" ", "")
            artistDao.insert(
                Artist(
                    artistId = artistId,
                    name = artistName
                )
            )
        }
        var trackId = trackDao.getIdByTitleAndArtist(title, artistName)
        if (trackId == null) {
            trackId = artistId + title.replace(" ", "")
            trackDao.insert(
                Track(
                    trackId = trackId,
                    title = title,
                    artistId = artistId
                )
            )
        }

        val tenMinutesAgo = listen.playedAt - 10 * 60 * 1000
        val recentCount = listenDao.countRecentListens(artistName + title, tenMinutesAgo)

        if (recentCount == 0) {
            val listenEntity = Listen(
                id = listen.playedAt.toString() + trackId,
                trackId = trackId,
                playedAt = listen.playedAt
            )
            listenDao.insert(listenEntity)
        }
    }

    override suspend fun delete(listen: ListenFull) {
        val artistId = artistDao.getIdByName(listen.artist)
        val trackId =
            artistId?.let { trackDao.getIdByTitleAndArtist(listen.title, it) }
        val listenId =
            trackId?.let {
                listenDao.getIdByTrackAndPlayedAt(
                    it,
                    listen.playedAt
                )
            }

        listenId?.let {
            val listen = Listen(
                id = it,
                trackId = trackId,
                playedAt = listen.playedAt
            )
            listenDao.delete(listen)
        }
    }

    override suspend fun getTopArtists(): List<TopArtist> {
        return listenDao.getTopArtists()
    }

    override suspend fun getTopTracks(): List<TopTrack> {
        return listenDao.getTopTracks()
    }
}