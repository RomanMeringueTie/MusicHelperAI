package com.example.music_helper.feature.analysis.impl.data.datasource

interface AIReviewDataSource {
    suspend fun get(prompt: String): String
}
