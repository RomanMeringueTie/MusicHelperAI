package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.TopArtist
import com.example.maps.data.model.TopTrack
import com.example.maps.domain.GetTopArtistsUseCase
import com.example.maps.domain.GetTopTracksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsViewModel(
    private val getTopArtistsUseCase: GetTopArtistsUseCase,
    private val getTopTracksUseCase: GetTopTracksUseCase,
) : ViewModel() {
    private val _artists = MutableStateFlow<State<List<TopArtist>>>(State.Loading)
    private val _tracks = MutableStateFlow<State<List<TopTrack>>>(State.Loading)
    private val _state =
        MutableStateFlow<State<Pair<List<TopArtist>, List<TopTrack>>>>(State.Loading)
    val state = _state.asStateFlow()

    init {
        loadTopArtists()
        loadTopTracks()
    }

    private fun loadTopArtists() {
        viewModelScope.launch {
            val artists = withContext(Dispatchers.IO) {
                getTopArtistsUseCase()
            }
            artists.fold(
                onSuccess = {
                    _artists.value = State.Content(it)
                    updateGlobalState()
                },
                onFailure = {
                    _artists.value = State.Failure(it.message ?: "Что-то пошло не так...")
                    updateGlobalState()
                }
            )
        }
    }

    private fun loadTopTracks() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getTopTracksUseCase()
            }
            result.fold(
                onSuccess = {
                    _tracks.value = State.Content(it)
                    updateGlobalState()
                },
                onFailure = {
                    _tracks.value = State.Failure(it.message ?: "Что-то пошло не так...")
                    updateGlobalState()
                }
            )
        }
    }

    private fun updateGlobalState() {
        val artistsState = _artists.value
        val tracksState = _tracks.value

        _state.value = when {
            artistsState is State.Loading || tracksState is State.Loading -> {
                State.Loading
            }

            artistsState is State.Failure -> {
                State.Failure(artistsState.message)
            }

            tracksState is State.Failure -> {
                State.Failure(tracksState.message)
            }

            artistsState is State.Content && tracksState is State.Content -> {
                State.Content(Pair(artistsState.data, tracksState.data))
            }

            else -> {
                State.Loading
            }
        }
    }
}