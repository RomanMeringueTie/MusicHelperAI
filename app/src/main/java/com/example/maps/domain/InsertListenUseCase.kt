package com.example.maps.domain

import com.example.maps.data.model.ListenFull

interface InsertListenUseCase {
    suspend operator fun invoke(listen: ListenFull)
}