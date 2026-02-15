package com.example.music_helper.domain

interface SaveUserUseCase {
    suspend operator fun invoke(userId: String)
}