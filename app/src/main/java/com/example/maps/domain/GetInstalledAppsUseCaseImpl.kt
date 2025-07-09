package com.example.maps.domain

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.maps.data.model.AppInfo

class GetInstalledAppsUseCaseImpl(private val packageManager: PackageManager) :
    GetInstalledAppsUseCase {
    @SuppressLint("QueryPermissionsNeeded")
    override fun invoke(): List<AppInfo> {
        val installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val result = mutableListOf<AppInfo>()
        installedApps
            .filter {
                (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0
            }
            .sortedBy {
                packageManager.getApplicationLabel(it).toString()
            }.forEach {
                result.add(
                    AppInfo(
                        packageName = it.packageName,
                        appName = packageManager.getApplicationLabel(it).toString(),
                        icon = packageManager.getApplicationIcon(it)
                    )
                )
            }
        return result
    }
}