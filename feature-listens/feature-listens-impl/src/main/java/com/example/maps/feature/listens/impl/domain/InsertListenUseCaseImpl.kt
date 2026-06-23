package com.example.maps.feature.listens.impl.domain

import com.example.maps.feature.listens.api.domain.InsertListenUseCase
import com.example.maps.feature.listens.api.model.ListenFull
import com.example.maps.feature.listens.api.data.repository.ListensRepository

class InsertListenUseCaseImpl(private val repository: ListensRepository) : InsertListenUseCase {
    override suspend fun invoke(listen: ListenFull) {
        repository.insert(listen)
    }
}
