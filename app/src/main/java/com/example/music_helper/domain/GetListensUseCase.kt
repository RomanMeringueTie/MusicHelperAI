package com.example.music_helper.domain

import com.example.music_helper.data.model.ListenFull

interface GetListensUseCase {
    suspend operator fun invoke(): Result<List<ListenFull>>
}
