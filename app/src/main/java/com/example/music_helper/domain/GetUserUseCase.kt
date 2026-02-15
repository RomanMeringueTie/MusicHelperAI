package com.example.music_helper.domain

interface GetUserUseCase {
    suspend operator fun invoke(): String
}