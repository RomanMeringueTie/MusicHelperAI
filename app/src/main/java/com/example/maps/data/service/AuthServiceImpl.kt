package com.example.maps.data.service

import com.example.maps.data.model.UserModel
import com.google.firebase.auth.FirebaseAuth

class AuthServiceImpl : AuthService {
    override fun signIn() {
        val user = FirebaseAuth.getInstance().currentUser
        UserModel.name = user?.displayName
        UserModel.picture = user?.photoUrl.toString()
        UserModel.isAuthorized = true
        UserModel.userId = user?.uid
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