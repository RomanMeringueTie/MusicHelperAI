package com.example.music_helper.feature.listens.impl.domain

import com.example.music_helper.feature.listens.api.domain.InsertListenUseCase
import com.example.music_helper.feature.listens.api.model.ListenFull
import com.example.music_helper.feature.listens.api.data.repository.ListensRepository

class InsertListenUseCaseImpl(private val repository: ListensRepository) : InsertListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.insert(listen)
    }
}
