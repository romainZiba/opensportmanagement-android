package com.zcorp.opensportmanagement.repository

sealed class State<T> {
    data class Progress<T>(var loading: Boolean) : State<T>()
    data class Success<T>(var data: T) : State<T>()
    data class Failure<T>(val msg: String) : State<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): State<T> = Progress(isLoading)

        fun <T> success(data: T): State<T> = Success(data)

        fun <T> failure(msg: String): State<T> = Failure(msg)
    }
}