package com.example.maps.domain

interface SaveUserUseCase {
    suspend operator fun invoke(userId: String)
}