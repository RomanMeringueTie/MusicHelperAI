package com.example.maps

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.rememberNavController
import com.example.maps.data.model.UserModel
import com.example.maps.ui.MainScreen
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
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
            MainScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = get(),
            )
        }
    }
}