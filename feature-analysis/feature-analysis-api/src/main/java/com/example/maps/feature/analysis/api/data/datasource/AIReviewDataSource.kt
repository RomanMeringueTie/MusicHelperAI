package com.example.maps.feature.analysis.api.data.datasource

interface AIReviewDataSource {
    suspend fun get(prompt: String): String
}