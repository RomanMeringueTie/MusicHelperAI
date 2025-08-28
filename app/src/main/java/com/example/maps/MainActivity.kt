package com.example.maps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationManagerCompat
import com.example.maps.data.model.UserModel
import com.example.maps.domain.SaveUserUseCase
import com.example.maps.ui.MainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val enabledPackages =
            NotificationManagerCompat.getEnabledListenerPackages(applicationContext)
        if (!enabledPackages.contains("com.example.maps")) {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
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
        val userId = UserModel.userId
        val saveUserUseCase: SaveUserUseCase = get()
        CoroutineScope(Dispatchers.IO).launch {
            saveUserUseCase(userId ?: "")
        }
    }
}