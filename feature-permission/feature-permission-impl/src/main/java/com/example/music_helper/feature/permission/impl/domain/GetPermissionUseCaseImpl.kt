package com.example.music_helper.feature.permission.impl.domain

import com.example.music_helper.feature.permission.api.domain.GetPermissionUseCase
import com.example.music_helper.feature.permission.api.data.datasource.PermissionDataSource

class GetPermissionUseCaseImpl(private val permissionDataSource: PermissionDataSource) :
    GetPermissionUseCase {
    override suspend fun invoke(): Boolean {
        val result = permissionDataSource.get()
        return result
    }
}
