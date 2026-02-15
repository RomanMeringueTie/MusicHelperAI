package com.example.music_helper.domain

import com.example.music_helper.data.datasource.UserDataSource

class SaveUserUseCaseImpl(private val userDataSource: UserDataSource) : SaveUserUseCase {
    override suspend fun invoke(userId: String) {
        userDataSource.set(userId)
    }
}