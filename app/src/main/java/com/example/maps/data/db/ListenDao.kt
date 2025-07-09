package com.example.maps.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.maps.data.model.ListenFull

@Dao
interface ListenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listen: Listen)

    @Delete
    suspend fun delete(listen: Listen)

    @Query("""
    SELECT tracks.title AS title, artists.name AS artist
    FROM listens
    JOIN tracks ON listens.trackId = tracks.trackId
    JOIN artists ON tracks.artistId = artists.artistId
""")
    suspend fun getAll(): List<ListenFull>
}