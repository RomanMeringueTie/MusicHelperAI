package com.example.maps.data.datasource

import android.util.Log
import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack
import com.example.maps.data.model.UserModel
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ListensRemoteDataSourceImpl(private val firestore: FirebaseFirestore) :
    ListensRemoteDataSource {
    override suspend fun getAll(): List<ListenFull> {
        val userId = UserModel.userId!!
        val listens = getUserListens(userId)
        val tracks = joinUserTracks(listens, userId)
        val result = joinUserArtists(tracks, userId)
        return result
    }

    private suspend fun getUserListens(userId: String): List<ListenFull> {
        val result = mutableListOf<ListenFull>()
        val listens = firestore.collection("listens")
            .whereEqualTo("userId", userId)
            .get().await()
            .documents
        for (listen in listens) {
            val playedAt = (listen.data?.get("playedAt") ?: 0L) as Long
            val trackId = (listen.data?.get("trackId") ?: 0L) as String
            result.add(
                ListenFull(
                    title = trackId.toString(),
                    artist = "",
                    playedAt = playedAt
                )
            )
        }
        return result
    }

    private suspend fun joinUserTracks(
        listens: List<ListenFull>,
        userId: String,
    ): List<ListenFull> {
        val result = mutableListOf<ListenFull>()
        val tracks = firestore.collection("tracks")
            .whereEqualTo("userId", userId)
            .get().await()
            .documents
        if (tracks.isNotEmpty()) {
            for (track in tracks) {
                val trackId = track["trackId"] as String
                val filteredListens = listens.filter {
                    it.title == trackId.toString()
                }
                filteredListens.forEach {
                    result.add(
                        it.copy(
                            title = track["title"].toString(),
                            artist = (track["artistId"] ?: 0L) as String
                        )
                    )
                }
            }
        }
        return result
    }

    private suspend fun joinUserArtists(
        tracks: List<ListenFull>,
        userId: String,
    ): List<ListenFull> {
        val result = mutableListOf<ListenFull>()
        val artists = firestore.collection("artists")
            .whereEqualTo("userId", userId)
            .get().await()
            .documents
        if (artists.isNotEmpty()) {
            for (track in tracks) {
                val artistId = track.artist
                val filteredArtists = artists.filter { it["artistId"] == artistId }
                filteredArtists.forEach {
                    result.add(
                        track.copy(artist = it["name"] as String)
                    )
                }
            }
        }
        return result
    }

    override suspend fun insert(listen: ListenFull) {
        val artists = firestore.collection("artists")
        val tracks = firestore.collection("tracks")
        val listens = firestore.collection("listens")
        val userId = UserModel.userId!!
        val tenMinutesAgo = listen.playedAt - 10 * 60 * 1000
        val lastListen = listens
            .whereEqualTo("userId", userId)
            .whereEqualTo("trackId", listen.artist.replace(" ", "") + listen.title.replace(" ", ""))
            .whereLessThan("playedAt", tenMinutesAgo)
            .get()
            .await()

        if (lastListen.documents.isNotEmpty())
            return

        val findArtists = artists
            .whereEqualTo("userId", userId)
            .whereEqualTo("name", listen.artist)
            .get().await()
            .documents
        val artistId = listen.artist.replace(" ", "")
        if (findArtists.isEmpty()) {
            val artist =
                hashMapOf(
                    "userId" to userId,
                    "artistId" to artistId,
                    "name" to listen.artist
                )
            artists.add(artist)
        }

        val findTracks = tracks
            .whereEqualTo("userId", userId)
            .whereEqualTo("title", listen.title)
            .whereEqualTo("artistId", artistId)
            .get().await()
            .documents
        val trackId = artistId + listen.title.replace(" ", "")
        if (findTracks.isEmpty()) {
            val track =
                hashMapOf(
                    "userId" to userId,
                    "trackId" to trackId,
                    "artistId" to artistId,
                    "title" to listen.title
                )
            tracks.add(track)
        }

        val listenId = listen.playedAt.toString() + trackId
        val listen =
            hashMapOf(
                "userId" to userId,
                "id" to listenId,
                "playedAt" to listen.playedAt,
                "trackId" to trackId
            )
        listens.add(listen)
    }

    override suspend fun delete(listenFull: ListenFull) {
        val userId = UserModel.userId!!
        val listens = firestore.collection("listens")
            .whereEqualTo("userId", userId)
            .whereEqualTo("playedAt", listenFull.playedAt)
            .get()
            .await()
            .documents
        for (listen in listens) {
            if (listen["trackId"] ==
                listenFull.artist.replace(" ", "")
                + listenFull.title.replace(" ", "")
            )
                listen.reference.delete()
        }
    }

    override suspend fun getTopArtists(): List<TopArtist> {
        val userId = UserModel.userId!!
        var count = AggregateSource.SERVER
        val result = mutableListOf<TopArtist>()
        val artists = firestore.collection("artists")
            .whereEqualTo("userId", userId)
            .get()
            .await()
        for (artist in artists) {
            val artistId = artist["artistId"] as String
            val tracks = firestore.collection("tracks")
                .whereEqualTo("userId", userId)
                .whereEqualTo("artistId", artistId)
                .get()
                .await()
            val trackIds = mutableListOf<String>()
            for (track in tracks.documents) {
                val trackId = track["trackId"] as String
                trackIds.add(trackId)
            }
            val listensCount = firestore.collection("listens")
                .whereEqualTo("userId", userId)
                .whereIn("trackId", trackIds)
                .count()
                .get(count)
                .await()
            if (listensCount.count != 0L) {
                val artistName = artist["name"] as String
                val topArtist = TopArtist(
                    artistName = artistName,
                    trackCount = listensCount.count.toInt()
                )
                result.add(topArtist)
            }
        }
        return result.sortedByDescending { it.trackCount }.take(5)
    }

    override suspend fun getTopTracks(): List<TopTrack> {
        val userId = UserModel.userId!!
        val result = mutableListOf<TopTrack>()
        val trackIds = mutableListOf<String>()
        val listens = firestore.collection("listens")
            .whereEqualTo("userId", userId)
            .get()
            .await()
        for (listen in listens) {
            val trackId = listen["trackId"] as String
            trackIds.add(trackId)
        }
        val topTrackIds = getMostFrequentStrings(trackIds, 5)

        for (topTrackId in topTrackIds) {
            val track = firestore.collection("tracks")
                .whereEqualTo("userId", userId)
                .whereEqualTo("trackId", topTrackId.first)
                .limit(1)
                .get()
                .await()
                .documents.first()
            val trackName = track["title"] as String

            val artistId = track["artistId"] as String
            val artist = firestore.collection("artists")
                .whereEqualTo("userId", userId)
                .whereEqualTo("artistId", artistId)
                .limit(1)
                .get()
                .await()
                .documents
                .first()
            val artistName = artist["name"] as String

            val topTrack = TopTrack(
                trackName = trackName,
                artistName = artistName,
                listenCount = topTrackId.second
            )
            result.add(topTrack)
        }
        return result
    }

    private fun getMostFrequentStrings(strings: List<String>, n: Int): List<Pair<String, Int>> {
        if (n <= 0 || strings.isEmpty()) {
            return emptyList()
        }

        return strings
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(n)
    }
}