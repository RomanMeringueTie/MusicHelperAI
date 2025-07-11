package com.example.maps.domain

import com.example.maps.data.model.ListenFull
import com.example.maps.data.repository.ListensRepository

class DeleteListenUseCaseImpl(private val repository: ListensRepository) : DeleteListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.delete(listen)
    }
}