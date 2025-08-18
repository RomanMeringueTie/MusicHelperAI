package com.example.maps.domain

import com.example.maps.data.model.UserModel

class SignOutUseCaseImpl: SignOutUseCase {
    override fun invoke() {
        UserModel.apply {
            isAuthorized = false
            name = null
            picture = null
        }
    }
}