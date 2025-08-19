package com.example.maps.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import com.example.maps.R
import com.example.maps.presentation.LoginViewModel
import com.example.maps.ui.utils.TypeWritingText
import com.example.maps.ui.utils.signInIntent
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

@Composable
fun LoginScreen(modifier: Modifier, viewModel: LoginViewModel, onRoute: () -> Unit) {

    val launcher =
        rememberLauncherForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
            onRoute()
            viewModel.onSignIn(res)
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