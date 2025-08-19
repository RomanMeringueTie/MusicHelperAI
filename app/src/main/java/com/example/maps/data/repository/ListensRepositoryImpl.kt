package com.example.maps.data.repository

import com.example.maps.data.db.Artist
import com.example.maps.data.db.ArtistDao
import com.example.maps.data.db.Listen
import com.example.maps.data.db.ListenDao
import com.example.maps.data.db.Track
import com.example.maps.data.db.TrackDao
import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack
import com.example.maps.data.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore

class ListensRepositoryImpl(
    private val listenDao: ListenDao,
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao,
    private val firestore: FirebaseFirestore,
) : ListensRepository {
    override suspend fun getAll(): List<ListenFull> {
        return listenDao.getAll()
    }

    override suspend fun insert(listenFull: ListenFull) {
        val artistName = listenFull.artist
        val title = listenFull.title

        val artistId = artistDao.getIdByName(artistName)
            ?: artistDao.insert(Artist(name = artistName))

        val trackId = trackDao.getIdByTitleAndArtist(title, artistId)
            ?: trackDao.insert(Track(title = title, artistId = artistId))

        val tenMinutesAgo = listenFull.playedAt - 10 * 60 * 1000

        val recentCount = listenDao.countRecentListens(trackId, tenMinutesAgo)

        if (recentCount == 0) {
            val listenEntity = Listen(trackId = trackId, playedAt = listenFull.playedAt)
            listenDao.insert(listenEntity)
            val listenId =
                trackId.let { listenDao.getIdByTrackAndPlayedAt(it, listenFull.playedAt) }

            if (UserModel.isAuthorized) {
                val artists = firestore.collection("artists")
                val tracks = firestore.collection("tracks")
                val listens = firestore.collection("listens")
                val userId = UserModel.userId

                val artist =
                    hashMapOf("userId" to userId, "artistId" to artistId, "name" to artistName)
                val track =
                    hashMapOf(
                        "userId" to userId,
                        "artistId" to artistId,
                        "title" to title,
                        "trackId" to trackId
                    )
                val listen =
                    hashMapOf(
                        "userId" to userId,
                        "id" to listenId,
                        "playedAt" to listenFull.playedAt,
                        "trackId" to trackId
                    )

                artists.document(userId + artistId).set(artist)
                tracks.document(userId + trackId).set(track)
                listens.document(userId + listenId).set(listen)
            }
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

            if (UserModel.isAuthorized) {
                val listens = firestore.collection("listens")
                listens.document(UserModel.userId + listenId).delete()
            }
        }
    }

    override suspend fun getTopArtists(): List<TopArtist> {
        return listenDao.getTopArtists()
    }

    override suspend fun getTopTracks(): List<TopTrack> {
        return listenDao.getTopTracks()
    }

}