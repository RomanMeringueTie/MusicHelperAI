package com.example.maps.data.datasource

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.maps.data.model.AppInfo

class InstalledAppsDataSourceImpl(
    private val packageManager: PackageManager,
    private val sharedPreferences: SharedPreferences,
) : InstalledAppsDataSource {
    @SuppressLint("QueryPermissionsNeeded")
    override suspend fun get(): List<AppInfo> {
        val installedApps =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val pickedApps = sharedPreferences.getStringSet("PICKED_APPS", emptySet<String>())
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
                        icon = packageManager.getApplicationIcon(it),
                        isPicked = pickedApps?.contains(it.packageName) == true
                    )
                )
            }
        return result
    }
}