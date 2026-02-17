package com.example.music_helper.feature.listens.impl.domain

import com.example.music_helper.feature.listens.api.domain.DeleteListenUseCase
import com.example.music_helper.feature.listens.api.model.ListenFull
import com.example.music_helper.feature.listens.api.data.repository.ListensRepository

class DeleteListenUseCaseImpl(private val repository: ListensRepository) : DeleteListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.delete(listen)
    }
}
