package com.example.maps.feature.listens.api.domain

import com.example.maps.feature.listens.api.model.ListenFull

interface GetListensUseCase {
    suspend operator fun invoke(): Result<List<ListenFull>>
}
