package com.example.maps.domain

interface GetUserUseCase {
    suspend operator fun invoke(): String
}