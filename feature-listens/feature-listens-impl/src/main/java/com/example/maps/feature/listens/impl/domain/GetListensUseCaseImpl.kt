package com.example.maps.feature.listens.impl.domain

import com.example.maps.feature.listens.api.domain.GetListensUseCase
import com.example.maps.feature.listens.api.model.ListenFull
import com.example.maps.feature.listens.api.data.repository.ListensRepository

class GetListensUseCaseImpl(private val listensRepository: ListensRepository) :
    GetListensUseCase {
    override suspend operator fun invoke(): Result<List<ListenFull>> {
        try {
            val result = listensRepository.getAll()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}
