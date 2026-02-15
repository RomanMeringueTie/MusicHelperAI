package com.example.music_helper.domain

import com.example.music_helper.data.model.ListenFull
import com.example.music_helper.data.repository.ListensRepository

class InsertListenUseCaseImpl(private val repository: ListensRepository) : InsertListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.insert(listen)
    }
}