package com.example.music_helper.feature.listens.api.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.music_helper.feature.listens.api.data.db.model.Artist

@Dao
interface ArtistDao {
    @Query("SELECT artistId FROM artists WHERE name = :name LIMIT 1")
    suspend fun getIdByName(name: String): String?

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insert(artist: Artist)
}