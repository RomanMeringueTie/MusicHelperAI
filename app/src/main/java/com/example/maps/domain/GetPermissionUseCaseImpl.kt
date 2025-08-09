package com.example.maps.domain

import com.example.maps.data.datasource.PermissionDataSource

class GetPermissionUseCaseImpl(private val permissionDataSource: PermissionDataSource) :
    GetPermissionUseCase {
    override suspend fun invoke(): Boolean {
        val result = permissionDataSource.get()
        return result
    }
}