package com.example.maps

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationManagerCompat
import com.example.maps.ui.MainScreen
import org.koin.android.ext.android.get

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
            MainScreen(modifier = Modifier.fillMaxSize(), viewModel = get())
        }
    }
}