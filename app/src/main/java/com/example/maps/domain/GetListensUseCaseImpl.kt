package com.example.maps.domain

import android.util.Log
import com.example.maps.data.model.ListenFull
import com.example.maps.data.repository.ListensRepository

class GetListensUseCaseImpl(private val listensRepository: ListensRepository) :
    GetListensUseCase {
    override suspend operator fun invoke(): Result<List<ListenFull>> {
        try {
            val result = listensRepository.getAll()
            Log.e("FUUUCK_USECASE", result.toString())
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}