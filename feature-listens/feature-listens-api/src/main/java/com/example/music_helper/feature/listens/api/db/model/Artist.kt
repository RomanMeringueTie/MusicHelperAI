package com.example.music_helper.feature.listens.api.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val artistId: String,
    val name: String
)