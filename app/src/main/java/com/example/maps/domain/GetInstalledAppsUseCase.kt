package com.example.maps.domain

import com.example.maps.data.model.AppInfo

interface GetInstalledAppsUseCase {
    operator fun invoke(): List<AppInfo>
}
