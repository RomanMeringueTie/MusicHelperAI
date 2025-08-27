package com.example.maps.data.datasource

interface UserDataSource {
    suspend fun get(): String

    suspend fun set(userId: String)
}