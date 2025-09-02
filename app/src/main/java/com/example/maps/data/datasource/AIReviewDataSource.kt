package com.example.maps.data.datasource

interface AIReviewDataSource {
    suspend fun get(prompt: String): String
}