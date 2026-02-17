package com.example.music_helper.feature.listens.api.domain

import com.example.music_helper.feature.listens.api.model.ListenFull

interface DeleteListenUseCase {
    suspend operator fun invoke(listen: ListenFull)
}
