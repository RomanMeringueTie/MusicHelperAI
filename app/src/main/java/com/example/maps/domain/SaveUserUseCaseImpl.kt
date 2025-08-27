package com.example.maps.domain

import com.example.maps.data.datasource.UserDataSource

class SaveUserUseCaseImpl(private val userDataSource: UserDataSource) : SaveUserUseCase {
    override suspend fun invoke(userId: String) {
        userDataSource.set(userId)
    }
}