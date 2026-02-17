package com.example.music_helper.feature.listens.api.domain

import com.example.music_helper.feature.listens.api.model.ListenFull

interface GetListensUseCase {
    suspend operator fun invoke(): Result<List<ListenFull>>
}
