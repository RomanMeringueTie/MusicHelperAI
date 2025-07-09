package com.example.maps.domain

import android.content.SharedPreferences
import androidx.core.content.edit

class SavePickedAppsUseCaseImpl(private val sharedPreferences: SharedPreferences) :
    SavePickedAppsUseCase {
    override fun invoke(pickedApps: Set<String>) {
        sharedPreferences.edit(commit = true) {
            putStringSet("PICKED_APPS", pickedApps)
            apply()
        }
    }
}