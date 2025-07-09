package com.example.maps.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtistDao {
    @Query("SELECT artistId FROM artists WHERE name = :name LIMIT 1")
    suspend fun getIdByName(name: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: Artist): Long
}