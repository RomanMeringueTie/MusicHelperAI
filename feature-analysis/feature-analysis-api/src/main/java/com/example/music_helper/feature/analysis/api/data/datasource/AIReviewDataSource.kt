package com.example.music_helper.feature.analysis.api.data.datasource

interface AIReviewDataSource {
    suspend fun get(prompt: String): String
}