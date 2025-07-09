package com.example.maps.domain

import com.example.maps.data.model.ListenFull

interface GetListensUseCase {
    suspend operator fun invoke(): Result<List<ListenFull>>
}
