package com.example.music_helper.presentation

interface State<out T> {
    data object Loading : State<Nothing>
    data class Content<T>(val data: T) : State<T>
    data class Failure(val message: String) : State<Nothing>
}