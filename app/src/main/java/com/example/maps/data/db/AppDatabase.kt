package com.example.maps.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Listen::class, Artist::class, Track::class],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ListenDao(): ListenDao
    abstract fun ArtistDao(): ArtistDao
    abstract fun TrackDao(): TrackDao
}