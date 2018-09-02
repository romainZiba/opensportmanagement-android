package com.zcorp.opensportmanagement.repository

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String) {
    companion object {
        // Remote data fetch success
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, "")
        }

        // Remote data fetch error
        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        // Data coming from db. Remote data may be fetch
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, "")
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}