package com.example.music_helper.feature.listens.impl.data.datasource

import com.example.music_helper.feature.listens.api.db.model.Artist
import com.example.music_helper.feature.listens.api.db.dao.ArtistDao
import com.example.music_helper.feature.listens.api.db.model.Listen
import com.example.music_helper.feature.listens.api.db.dao.ListenDao
import com.example.music_helper.feature.listens.api.db.model.Track
import com.example.music_helper.feature.listens.api.db.dao.TrackDao
import com.example.music_helper.feature.listens.api.model.ListenFull
import com.example.music_helper.feature.listens.api.model.TopArtist
import com.example.music_helper.feature.listens.api.model.TopTrack

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

    override suspend fun delete(listenFull: ListenFull) {
        val artistId = artistDao.getIdByName(listenFull.artist)
        val trackId =
            artistId?.let { trackDao.getIdByTitleAndArtist(listenFull.title, it) }
        val listenId =
            trackId?.let {
                listenDao.getIdByTrackAndPlayedAt(
                    it,
                    listenFull.playedAt
                )
            }

        listenId?.let {
            val listen = Listen(
                id = it,
                trackId = trackId,
                playedAt = listenFull.playedAt
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
