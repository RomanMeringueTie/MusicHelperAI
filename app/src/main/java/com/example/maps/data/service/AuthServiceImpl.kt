package com.example.maps.data.service

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import com.example.maps.R
import com.example.maps.data.model.UserModel
import com.example.maps.ui.utils.getGreeting
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class AuthServiceImpl(
    private val context: Context,
) : AuthService {
    override fun signIn(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            val name = user?.displayName
            val greeting = getGreeting(name.toString())
            Toast.makeText(context, greeting, Toast.LENGTH_SHORT).show()
            UserModel.name = name
            UserModel.picture = user?.photoUrl.toString()
            UserModel.isAuthorized = true
            UserModel.userId = user?.uid
            val vibratorManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
            val vibrationEffect =
                VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
            vibratorManager.vibrate(vibrationEffect)
        } else {
            val message = response?.error?.message ?: context.getString(R.string.auth_error)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun signOut() {
        UserModel.apply {
            userId = null
            isAuthorized = false
            name = null
            picture = null
        }
    }
}