package com.example.maps.domain

import com.example.maps.data.datasource.UserDataSource

class GetUserUseCaseImpl(private val userDataSource: UserDataSource) : GetUserUseCase {
    override suspend fun invoke(): String {
        val userId = userDataSource.get()
        return userId
    }
}