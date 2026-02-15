package com.example.music_helper.data.datasource

interface UserDataSource {
    suspend fun get(): String

    suspend fun set(userId: String)
}