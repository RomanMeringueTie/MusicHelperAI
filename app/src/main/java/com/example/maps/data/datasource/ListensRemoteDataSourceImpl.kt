package com.example.maps.data.datasource

import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack
import com.example.maps.data.model.UserSingleton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ListensRemoteDataSourceImpl(private val firestore: FirebaseFirestore) :
    ListensRemoteDataSource {
    override suspend fun getAll(): List<ListenFull> {
        val userId = UserSingleton.userId!!
        val result = mutableListOf<ListenFull>()
        val listens = firestore.collection("listens")
            .whereEqualTo("userId", userId)
            .get().await()
            .documents
        for (listen in listens) {
            val playedAt = (listen.data?.get("playedAt") ?: 0L) as Long
            val track = (listen.data?.get("track") ?: 0L) as String
            val artist = (listen.data?.get("artist") ?: 0L) as String
            result.add(
                ListenFull(
                    title = track,
                    artist = artist,
                    playedAt = playedAt
                )
            )
        }
        return result
    }

    override suspend fun insert(listen: ListenFull) {
        val userId = UserSingleton.userId!!

        val listen =
            hashMapOf(
                "userId" to userId,
                "playedAt" to listen.playedAt,
                "track" to listen.title,
                "artist" to listen.artist
            )
        firestore.collection("listens").add(listen)
    }

    override suspend fun delete(listenFull: ListenFull) {
        val userId = UserSingleton.userId!!
        val listens = firestore.collection("listens")
            .whereEqualTo("userId", userId)
            .whereEqualTo("playedAt", listenFull.playedAt)
            .whereEqualTo("artist", listenFull.artist)
            .whereEqualTo("track", listenFull.title)
            .get()
            .await()
            .documents
        for (listen in listens) {
            listen.reference.delete()
        }
    }

    override suspend fun getTopArtists(): List<TopArtist> {
        val userId = UserSingleton.userId!!
        val result = mutableListOf<TopArtist>()
        val trackStrings = mutableListOf<String>()
        val listens = firestore.collection("listens")
            .whereEqualTo("userId", userId)
            .get()
            .await()
        for (listen in listens) {
            val trackString = listen["artist"] as String
            trackStrings.add(trackString)
        }
        val topArtists = getMostFrequent(trackStrings, 5)

        for (topArtist in topArtists) {
            val topTrack = TopArtist(
                artistName = topArtist.first as String,
                trackCount = topArtist.second
            )
            result.add(topTrack)
        }
        return result
    }

    override suspend fun getTopTracks(): List<TopTrack> {
        val userId = UserSingleton.userId!!
        val result = mutableListOf<TopTrack>()
        val trackStrings = mutableListOf<Pair<String, String>>()
        val listens = firestore.collection("listens")
            .whereEqualTo("userId", userId)
            .get()
            .await()
        for (listen in listens) {
            val trackString =  Pair(listen["artist"] as String, listen["track"] as String)
            trackStrings.add(trackString)
        }
        val topTracks = getMostFrequent(trackStrings, 5)

        for (topTrack in topTracks) {

            val topTrack = TopTrack(
                trackName = (topTrack as Pair<Pair<String, String>, Int>).first.second,
                artistName = topTrack.first.first,
                listenCount = topTrack.second
            )
            result.add(topTrack)
        }
        return result
    }

    private fun getMostFrequent(strings: Iterable<Any>, n: Int): List<Pair<Any, Int>> {
        if (n <= 0) {
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