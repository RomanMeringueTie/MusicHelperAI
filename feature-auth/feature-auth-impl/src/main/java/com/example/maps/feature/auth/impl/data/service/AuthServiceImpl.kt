package com.example.maps.feature.auth.impl.data.service

import com.example.maps.common.api.model.SettingsSingleton
import com.example.maps.common.api.model.UserSingleton
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
        SettingsSingleton.isGuest = false
    }

    override fun signOut() {
        UserSingleton.apply {
            userId = null
            isAuthorized = false
            name = null
            picture = null
        }
        SettingsSingleton.isGuest = true
    }
}
