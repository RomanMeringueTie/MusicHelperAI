package com.example.maps.ui

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import com.example.maps.R
import com.example.maps.presentation.LoginViewModel
import com.example.maps.ui.utils.TypeWritingText
import com.example.maps.ui.utils.signInIntent
import com.example.maps.ui.utils.vibrate
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun LoginScreen(modifier: Modifier, viewModel: LoginViewModel, onRoute: () -> Unit) {

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
            onRoute()
            if (res.resultCode == RESULT_OK) {
                viewModel.onSignIn()
                val user = FirebaseAuth.getInstance().currentUser
                val name = user?.displayName
                successToast(context, name ?: context.getString(R.string.unknown))
                vibrate(context, 300L)
            } else {
                val result = res.idpResponse
                val error = result?.error?.message ?: context.getString(R.string.auth_error)
                failureToast(context, error)
            }
        }

    LaunchedEffect(Unit) { launcher.launch(signInIntent) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            TypeWritingText(
                text = stringResource(R.string.auth),
                style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                100L
            )
        }
    }

}

private fun successToast(context: Context, name: String) {
    val greeting = getGreeting(context, name)
    Toast.makeText(context, greeting, Toast.LENGTH_SHORT).show()
}

private fun failureToast(context: Context, error: String) {
    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
}

@SuppressLint("SimpleDateFormat")
private fun getGreeting(context: Context, name: String): String {
    val sdf = SimpleDateFormat("HH", Locale.getDefault())
    val currentTime = Calendar.getInstance().time
    val currentHour = sdf.format(currentTime).toInt()
    val greeting = if (currentHour < 5) {
        context.getString(R.string.night_greeting)
    } else if (currentHour < 12) {
        context.getString(R.string.morning_greeting)
    } else if (currentHour < 18) {
        context.getString(R.string.day_greeting)
    } else context.getString(R.string.afternoon_greeting)
    return "$greeting, $name!"
}