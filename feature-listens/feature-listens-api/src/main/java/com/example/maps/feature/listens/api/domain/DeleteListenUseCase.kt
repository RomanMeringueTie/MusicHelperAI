package com.example.maps.feature.listens.api.domain

import com.example.maps.feature.listens.api.model.ListenFull

interface DeleteListenUseCase {
    suspend operator fun invoke(listen: ListenFull)
}
