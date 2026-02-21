package com.example.music_helper.feature.listens.api.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.music_helper.feature.listens.api.data.db.model.Track

@Dao
interface TrackDao {
    @Query("SELECT trackId FROM tracks WHERE title = :title AND artistId = :artistId LIMIT 1")
    suspend fun getIdByTitleAndArtist(title: String, artistId: String): String?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(track: Track)
}
