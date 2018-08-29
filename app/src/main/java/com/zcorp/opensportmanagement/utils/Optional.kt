package com.zcorp.opensportmanagement.utils

class Optional<T> {

    private var value: T? = null

    private constructor() {
        this.value = null
    }

    private constructor(value: T) {
        this.value = value
    }

    fun isPresent(): Boolean {
        return value != null
    }

    fun isNotPresent(): Boolean {
        return value == null
    }

    fun get(): T {
        return this.value!!
    }

    companion object {

        fun <T> empty(): Optional<T> {
            return Optional()
        }

        fun <T> of(value: T): Optional<T> {
            return Optional(value)
        }
    }

}