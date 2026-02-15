package com.example.music_helper.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "listens",
    foreignKeys = [ForeignKey(
        entity = Track::class,
        parentColumns = ["trackId"],
        childColumns = ["trackId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("trackId")]
)

data class Listen(
    @PrimaryKey val id: String,
    val trackId: String,
    val playedAt: Long
)