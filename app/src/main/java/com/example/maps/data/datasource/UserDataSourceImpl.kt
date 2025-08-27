package com.example.maps.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit

class UserDataSourceImpl(private val sharedPreferences: SharedPreferences) : UserDataSource {
    override suspend fun get(): String {
        val result = sharedPreferences.getString("USER_ID", "")
        return result ?: ""
    }

    override suspend fun set(userId: String) {
        sharedPreferences.edit(commit = true) {
            putString("USER_ID", userId)
            apply()
        }
    }
}