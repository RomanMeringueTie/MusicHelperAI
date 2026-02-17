package com.example.music_helper.feature.settings.impl.domain

import com.example.music_helper.feature.settings.api.domain.GetUserUseCase
import com.example.music_helper.feature.settings.impl.data.datasource.UserDataSource

class GetUserUseCaseImpl(private val userDataSource: UserDataSource) : GetUserUseCase {
    override suspend fun invoke(): String {
        val userId = userDataSource.get()
        return userId
    }
}
