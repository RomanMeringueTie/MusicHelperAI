package com.example.maps.data.datasource

import com.example.maps.data.model.Review

interface ListensReviewDataSource {
    suspend fun get(listens: String): Review
}