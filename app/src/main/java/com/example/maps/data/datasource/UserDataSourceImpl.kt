package com.example.maps.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit

class UserDataSourceImpl(private val sharedPreferences: SharedPreferences) : UserDataSource {

    private companion object {
        const val USER_ID_KEY = "USER_ID"
    }

    override suspend fun get(): String {
        val result = sharedPreferences.getString(USER_ID_KEY, "")
        return result ?: ""
    }

    override suspend fun set(userId: String) {
        sharedPreferences.edit(commit = true) {
            putString(USER_ID_KEY, userId)
            apply()
        }
    }
}