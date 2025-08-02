package com.example.maps.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack

@Dao
interface ListenDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(listen: Listen)

    @Delete
    suspend fun delete(listen: Listen)

    @Query("""
    SELECT id FROM listens 
    WHERE trackId = :trackId AND playedAt = :playedAt
""")
    suspend fun getIdByTrackAndPlayedAt(trackId: Long, playedAt: Long): Long

    @Query("""
    SELECT COUNT(*) FROM listens 
    WHERE trackId = :trackId AND playedAt >= :minTime
""")
    suspend fun countRecentListens(trackId: Long, minTime: Long): Int

    @Query("""
    SELECT tracks.title AS title, artists.name AS artist, listens.playedAt AS playedAt
    FROM listens
    JOIN tracks ON listens.trackId = tracks.trackId
    JOIN artists ON tracks.artistId = artists.artistId
    ORDER BY listens.playedAt DESC
""")
    suspend fun getAll(): List<ListenFull>

    @Query("""
    SELECT artists.name AS artistName, COUNT(*) AS trackCount
    FROM listens
    JOIN tracks ON listens.trackId = tracks.trackId
    JOIN artists ON tracks.artistId = artists.artistId
    GROUP BY artists.artistId, artists.name
    ORDER BY trackCount DESC
    LIMIT 3
""")
    suspend fun getTopArtists(): List<TopArtist>

    @Query("""
    SELECT tracks.title AS trackName, artists.name AS artistName, COUNT(*) AS listenCount
    FROM listens
    JOIN tracks ON listens.trackId = tracks.trackId
    JOIN artists ON tracks.artistId = artists.artistId
    GROUP BY tracks.trackId, tracks.title, artists.name
    ORDER BY listenCount DESC
    LIMIT 3
""")
    suspend fun getTopTracks(): List<TopTrack>
}