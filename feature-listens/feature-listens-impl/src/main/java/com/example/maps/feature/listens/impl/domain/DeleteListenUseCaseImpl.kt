package com.example.maps.feature.listens.impl.domain

import com.example.maps.feature.listens.api.domain.DeleteListenUseCase
import com.example.maps.feature.listens.api.model.ListenFull
import com.example.maps.feature.listens.api.data.repository.ListensRepository

class DeleteListenUseCaseImpl(private val repository: ListensRepository) : DeleteListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.delete(listen)
    }
}
