package com.example.music_helper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.music_helper.data.datasource.SettingsDataSource
import com.example.music_helper.data.model.UserSingleton
import com.example.music_helper.domain.SaveUserUseCase
import com.example.music_helper.ui.MainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val settingsDataSource: SettingsDataSource by lazy { get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSettings()

        enableEdgeToEdge()
        setContent {
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = koinViewModel(),
            )
        }
    }

    override fun onPause() {
        super.onPause()
        saveSettings()
    }

    private fun getSettings() {
        CoroutineScope(Dispatchers.IO).launch {
            settingsDataSource.get()
        }
    }

    private fun saveSettings() {
        CoroutineScope(Dispatchers.IO).launch {
            settingsDataSource.save()
            val userId = UserSingleton.userId
            val saveUserUseCase: SaveUserUseCase = get()
            saveUserUseCase(userId ?: "")
        }
    }
}