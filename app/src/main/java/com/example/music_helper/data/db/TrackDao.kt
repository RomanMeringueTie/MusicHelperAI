package com.example.music_helper.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrackDao {
    @Query("SELECT trackId FROM tracks WHERE title = :title AND artistId = :artistId LIMIT 1")
    suspend fun getIdByTitleAndArtist(title: String, artistId: String): String?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(track: Track)
}