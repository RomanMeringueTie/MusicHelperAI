package com.example.maps.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val artistId: String,
    val name: String
)