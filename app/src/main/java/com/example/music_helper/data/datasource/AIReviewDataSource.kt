package com.example.music_helper.data.datasource

interface AIReviewDataSource {
    suspend fun get(prompt: String): String
}