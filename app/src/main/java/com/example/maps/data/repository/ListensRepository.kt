package com.example.maps.data.repository

import com.example.maps.data.model.ListenFull

interface ListensRepository {
    suspend fun getAll(): List<ListenFull>

    suspend fun insert(listenFull: ListenFull)

    suspend fun delete(listenFull: ListenFull)
}