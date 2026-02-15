package com.example.music_helper.domain

import com.example.music_helper.data.model.ListenFull
import com.example.music_helper.data.repository.ListensRepository

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