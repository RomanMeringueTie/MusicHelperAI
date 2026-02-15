package com.example.music_helper.domain

import com.example.music_helper.data.model.ListenFull
import com.example.music_helper.data.repository.ListensRepository

class DeleteListenUseCaseImpl(private val repository: ListensRepository) : DeleteListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.delete(listen)
    }
}