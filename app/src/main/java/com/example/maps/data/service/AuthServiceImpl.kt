package com.example.maps.data.service

import com.example.maps.data.model.UserSingleton
import com.google.firebase.auth.FirebaseAuth

class AuthServiceImpl : AuthService {
    override fun signIn() {
        val user = FirebaseAuth.getInstance().currentUser
        UserSingleton.name = user?.displayName
        UserSingleton.picture = user?.photoUrl.toString()
        UserSingleton.isAuthorized = true
        UserSingleton.userId = user?.uid
    }

    override fun signOut() {
        UserSingleton.apply {
            userId = null
            isAuthorized = false
            name = null
            picture = null
        }
    }
}