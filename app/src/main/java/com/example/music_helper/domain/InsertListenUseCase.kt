package com.example.music_helper.domain

import com.example.music_helper.data.model.ListenFull

interface InsertListenUseCase {
    suspend operator fun invoke(listen: ListenFull)
}