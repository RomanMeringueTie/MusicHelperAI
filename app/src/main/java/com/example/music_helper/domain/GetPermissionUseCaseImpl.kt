package com.example.music_helper.domain

import com.example.music_helper.data.datasource.PermissionDataSource

class GetPermissionUseCaseImpl(private val permissionDataSource: PermissionDataSource) :
    GetPermissionUseCase {
    override suspend fun invoke(): Boolean {
        val result = permissionDataSource.get()
        return result
    }
}