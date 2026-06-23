package com.example.maps.feature.permission.impl.domain

import com.example.maps.feature.permission.api.domain.GetPermissionUseCase
import com.example.maps.feature.permission.api.data.datasource.PermissionDataSource

class GetPermissionUseCaseImpl(private val permissionDataSource: PermissionDataSource) :
    GetPermissionUseCase {
    override suspend fun invoke(): Boolean {
        val result = permissionDataSource.get()
        return result
    }
}
