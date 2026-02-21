package com.example.music_helper.feature.listens.api.data.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks",
    foreignKeys = [ForeignKey(
        entity = Artist::class,
        parentColumns = ["artistId"],
        childColumns = ["artistId"],
        onDelete = ForeignKey.Companion.CASCADE
    )],
    indices = [Index("artistId")]
)
data class Track(
    @PrimaryKey val trackId: String,
    val title: String,
    val artistId: String
)