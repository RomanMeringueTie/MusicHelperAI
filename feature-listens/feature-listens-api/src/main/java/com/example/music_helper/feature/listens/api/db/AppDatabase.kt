package com.example.music_helper.feature.listens.api.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.music_helper.feature.listens.api.db.dao.ArtistDao
import com.example.music_helper.feature.listens.api.db.dao.ListenDao
import com.example.music_helper.feature.listens.api.db.dao.TrackDao
import com.example.music_helper.feature.listens.api.db.model.Artist
import com.example.music_helper.feature.listens.api.db.model.Listen
import com.example.music_helper.feature.listens.api.db.model.Track

@Database(
    entities = [Listen::class, Artist::class, Track::class],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ListenDao(): ListenDao
    abstract fun ArtistDao(): ArtistDao
    abstract fun TrackDao(): TrackDao
}
