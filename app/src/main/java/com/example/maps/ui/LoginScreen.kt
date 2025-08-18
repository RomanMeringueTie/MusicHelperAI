package com.example.maps.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
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
import com.example.maps.data.model.UserModel
import com.example.maps.ui.utils.TypeWritingText
import com.example.maps.ui.utils.getGreeting
import com.example.maps.ui.utils.signInIntent
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(modifier: Modifier, onRoute: () -> Unit) {

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
            onRoute()
            onSignInResult(context, res)
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

private fun onSignInResult(
    context: Context,
    result: FirebaseAuthUIAuthenticationResult,
) {
    val response = result.idpResponse
    if (result.resultCode == RESULT_OK) {
        val user = FirebaseAuth.getInstance().currentUser
        val name = user?.displayName
        val greeting = getGreeting(name.toString())
        Toast.makeText(context, greeting, Toast.LENGTH_SHORT).show()
        UserModel.name = name
        UserModel.picture = user?.photoUrl.toString()
        UserModel.isAuthorized = true
        val vibratorManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        val vibrationEffect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
        vibratorManager.vibrate(vibrationEffect)
    } else {
        val message = response?.error?.message ?: context.getString(R.string.auth_error)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}