package com.example.maps.domain

import android.content.SharedPreferences

class GetPickedAppsUseCaseImpl(private val sharedPreferences: SharedPreferences) :
    GetPickedAppsUseCase {
    override fun invoke(): Result<Set<String>> {
        val result = sharedPreferences.getStringSet("PICKED_APPS", emptySet<String>())
        if (result == null || result.isEmpty()) {
            return Result.failure(Exception("No picked apps"))
        }
        return Result.success(result)
    }
}