package com.example.maps.domain

import com.example.maps.data.model.ListenFull
import com.example.maps.data.repository.ListensRepository

class InsertListenUseCaseImpl(private val repository: ListensRepository) : InsertListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.insert(listen)
    }
}