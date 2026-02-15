package com.example.music_helper.domain

import com.example.music_helper.data.datasource.UserDataSource

class GetUserUseCaseImpl(private val userDataSource: UserDataSource) : GetUserUseCase {
    override suspend fun invoke(): String {
        val userId = userDataSource.get()
        return userId
    }
}