package com.example.music_helper.feature.listens.api.db.model

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
        onDelete = ForeignKey.Companion.CASCADE
    )],
    indices = [Index("trackId")]
)

data class Listen(
    @PrimaryKey val id: String,
    val trackId: String,
    val playedAt: Long
)