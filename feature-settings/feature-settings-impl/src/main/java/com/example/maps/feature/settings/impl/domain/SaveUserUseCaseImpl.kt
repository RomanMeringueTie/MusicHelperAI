package com.example.maps.feature.settings.impl.domain

import com.example.maps.feature.settings.api.domain.SaveUserUseCase
import com.example.maps.feature.settings.impl.data.datasource.UserDataSource

class SaveUserUseCaseImpl(private val userDataSource: UserDataSource) : SaveUserUseCase {
    override suspend fun invoke(userId: String) {
        userDataSource.set(userId)
    }
}
