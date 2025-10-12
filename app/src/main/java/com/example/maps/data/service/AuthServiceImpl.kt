package com.example.maps.data.service

import android.util.Log
import com.example.maps.data.model.SettingsSingleton
import com.example.maps.data.model.UserSingleton
import com.google.firebase.auth.FirebaseAuth

class AuthServiceImpl : AuthService {
    override fun signIn() {
        val user = FirebaseAuth.getInstance().currentUser
        UserSingleton.apply {
            isAuthorized = true
            name = user?.displayName
            picture = user?.photoUrl.toString()
            userId = user?.uid
        }
        Log.e("FUUUCK_AUTH", "isGuest = false")
        SettingsSingleton.isGuest = false
    }

    override fun signOut() {
        UserSingleton.apply {
            userId = null
            isAuthorized = false
            name = null
            picture = null
        }
        Log.e("FUUUCK_AUTH", "isGuest = true")
        SettingsSingleton.isGuest = true
    }
}