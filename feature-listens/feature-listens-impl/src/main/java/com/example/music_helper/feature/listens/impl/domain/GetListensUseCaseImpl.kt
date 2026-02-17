package com.example.music_helper.feature.listens.impl.domain

import com.example.music_helper.feature.listens.api.domain.GetListensUseCase
import com.example.music_helper.feature.listens.api.model.ListenFull
import com.example.music_helper.feature.listens.api.data.repository.ListensRepository

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
